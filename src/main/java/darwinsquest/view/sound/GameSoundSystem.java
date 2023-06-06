package darwinsquest.view.sound;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * Class that represents the game sound system.
 */
public final class GameSoundSystem {

    private static MediaPlayer bgmPlayer;
    private static MediaPlayer introPlayer;
    private static MediaPlayer sfxPlayer;
    private static double masterVolume = 1.0;
    private static final double MIN_VOLUME = 0.0;
    private static final double MAX_VOLUME = 1.0;
    private static final Map<String, Pair<Media, Optional<Duration>>> SOUND_SYSTEM = loadSounds();

    private GameSoundSystem() {
    }

    /**
     * Plays a short sound effect.
     * This method is not ideal to play longer audio files, use
     * {@link GameSoundSystem#playMusic} instead.
     * This method does not stop any currently played sound.
     * @param sfx the sound effect file name.
     * @see GameSoundSystem#playSfx(String, Consumer, Consumer)
     */
    public static void playSfx(final String sfx) {
        final var sfxMedia = new Media(SOUND_SYSTEM.get(sfx).getKey().getSource());
        sfxPlayer = new MediaPlayer(sfxMedia);
        sfxPlayer.setVolume(masterVolume);
        sfxPlayer.play();
    }

    /**
     * Plays a short sound effect.
     * This method is not ideal to play longer audio files, use
     * {@link GameSoundSystem#playMusic} instead.
     * @param sfx the sound effect file name.
     * @param beforeAction a request to accept before playing the sfx.
     * @param afterAction a request to accept after playing the sfx.
     * @see GameSoundSystem#playSfx(String)
     */
    public static void playSfx(
            final String sfx,
            final Consumer<MediaPlayer> beforeAction,
            final Consumer<MediaPlayer> afterAction
    ) {
        checkSoundPresence(sfx);
        acceptIfMediaPlayerNotNull(beforeAction, bgmPlayer);
        final var media = new Media(SOUND_SYSTEM.get(sfx).getKey().getSource());
        sfxPlayer =  new MediaPlayer(media);
        sfxPlayer.setVolume(masterVolume);
        sfxPlayer.setOnEndOfMedia(() -> {
            if (!mediaPlayerStatusEquals(bgmPlayer, MediaPlayer.Status.PLAYING)) {
                acceptIfMediaPlayerNotNull(afterAction, bgmPlayer);
            }
        });
        sfxPlayer.play();
    }

    /**
     * Plays background music.
     * @param music the music file name.
     * @param loop {@code true} to loop the music.
     * @see GameSoundSystem#playIntroAndMusic
     */
    public static void playMusic(final String music, final boolean loop) {
        checkSoundPresence(music);
        stopIfPlaying(bgmPlayer);
        bgmPlayer = new MediaPlayer(SOUND_SYSTEM.get(music).getKey());
        bgmPlayer.setVolume(masterVolume);
        if (loop) {
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        bgmPlayer.setStartTime(Duration.ZERO);
        bgmPlayer.setStopTime(SOUND_SYSTEM.get(music).getValue().orElse(Duration.INDEFINITE));
        bgmPlayer.play();
    }

    /**
     * Plays an intro once, then plays the main music in loop.
     * @param intro the intro file name.
     * @param main the main music file name.
     * @see GameSoundSystem#playMusic
     */
    public static void playIntroAndMusic(final String intro, final String main) {
        checkSoundPresence(List.of(intro, main));
        stopIfPlaying(bgmPlayer);
        final var introPair = SOUND_SYSTEM.get(intro);
        final var mainPair = SOUND_SYSTEM.get(main);
        introPlayer = new MediaPlayer(introPair.getKey());
        bgmPlayer = new MediaPlayer(mainPair.getKey());
        bgmPlayer.setVolume(masterVolume);
        introPlayer.setVolume(masterVolume);
        introPlayer.setStartTime(Duration.ZERO);
        introPlayer.setStopTime(introPair.getValue().orElse(Duration.INDEFINITE));
        introPlayer.setOnEndOfMedia(() -> {
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.setStartTime(Duration.ZERO);
            bgmPlayer.setStopTime(mainPair.getValue().orElse(Duration.INDEFINITE));
            bgmPlayer.play();
        });
        introPlayer.play();
    }

    /**
     * This method stops all currently playing music and sfx,
     * preventing sound overlapping.
     */
    public static void stopAll() {
        stopIfPlaying(bgmPlayer);
        stopIfPlaying(introPlayer);
        stopIfPlaying(sfxPlayer);
    }

    /**
     * Retrieves master volume.
     * @return the master volume.
     */
    public static double getMasterVolume() {
        return masterVolume;
    }

    /**
     * This method sets the audio playback volume of all media players.
     * The accepted range is {@code [0.0, 1.0]}.
     * @param value the volume value.
     */
    public static void setMasterVolume(final double value) {
        if (!isVolumeInBounds(value)) {
            throw new IllegalArgumentException("Volume range must be [0.0, 1.0]. Provided: " + value);
        }
        masterVolume = value;
        Stream.of(bgmPlayer, introPlayer, sfxPlayer)
                .filter(Objects::nonNull)
                .forEach(mediaPlayer -> mediaPlayer.setVolume(value));
    }

    /**
     * Set the duration of a specific sound. This allows trimming a sound possible.
     * This method does nothing if the specified duration is greater than the original
     * sound duration.
     * @param sound the sound file name.
     * @param duration the new duration.
     */
    public static void setDuration(final String sound, final Duration duration) {
        checkSoundPresence(sound);
        final var soundPair = SOUND_SYSTEM.get(sound);
        if (soundPair.getKey().getDuration().greaterThan(duration)) {
            soundPair.setValue(Optional.ofNullable(duration));
        }
    }

    private static void checkSoundPresence(final String sound) {
        if (!SOUND_SYSTEM.containsKey(sound)) {
            throw new IllegalArgumentException(sound + " is not present in sound system.");
        }
    }

    private static void checkSoundPresence(final List<String> sounds) {
        if (sounds.stream().noneMatch(SOUND_SYSTEM::containsKey)) {
            throw new IllegalArgumentException(sounds + " are not present in sound system.");
        }
    }

    private static void stopIfPlaying(final MediaPlayer mediaPlayer) {
        if (Objects.nonNull(mediaPlayer) && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.stop();
        }
    }

    private static boolean mediaPlayerStatusEquals(final MediaPlayer mediaPlayer, final MediaPlayer.Status status) {
        return Objects.nonNull(mediaPlayer) && mediaPlayer.getStatus().equals(status);
    }

    private static void acceptIfMediaPlayerNotNull(final Consumer<MediaPlayer> consumer, final MediaPlayer mediaPlayer) {
        if (Objects.nonNull(mediaPlayer)) {
            consumer.accept(mediaPlayer);
        }
    }

    private static boolean isVolumeInBounds(final double value) {
        return Double.compare(value, MIN_VOLUME) >= 0 && Double.compare(value, MAX_VOLUME) <= 0;
    }

    private static Map<String, Pair<Media, Optional<Duration>>> loadSounds() {
        final File jarFile = new File(GameSoundSystem.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (jarFile.isFile()) {    // Run with JAR.
            return jarLoadSounds(jarFile);
        } else {    // Run with IDE.
            return ideLoadSounds();
        }
    }

    private static Map<String, Pair<Media, Optional<Duration>>> jarLoadSounds(final File jarFile) {
        final JarFile jar;
        try {
            jar = new JarFile(jarFile);
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        final Map<String, Pair<Media, Optional<Duration>>> copyMap = new HashMap<>();
        final Enumeration<JarEntry> entries = jar.entries();    // Returns all jar entries.
        while (entries.hasMoreElements()) {
            final String fullName = entries.nextElement().getName();
            if (fullName.endsWith(".wav") || fullName.endsWith(".mp3")) {
                // Getting the file name only.
                final int lastIndex = fullName.lastIndexOf('/');
                final String fileName = fullName.substring(lastIndex + 1);
                copyMap.put(fileName,
                        new MutablePair<>(
                                new Media(Objects.requireNonNull(
                                        GameSoundSystem.class.getClassLoader().getResource(fullName)).toExternalForm()),
                                Optional.empty()));
            }
        }
        try {
            jar.close();
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        return new HashMap<>(copyMap);
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", justification = "requireNonNull is used.")
    private static Map<String, Pair<Media, Optional<Duration>>> ideLoadSounds() {
        final Map<String, Pair<Media, Optional<Duration>>> copyMap = new HashMap<>();
        final URL url = Objects.requireNonNull(GameSoundSystem.class.getResource("/music"));
        try {
            final File files = new File(url.toURI());
            for (final File file : Objects.requireNonNull(files.listFiles())) {
                copyMap.put(file.getName(),
                        new MutablePair<>(new Media(Paths.get(file.getPath()).toUri().toString()),
                                Optional.empty()));
            }
        } catch (URISyntaxException e) {
            return Collections.emptyMap();
        }
        return new HashMap<>(copyMap);
    }

}
