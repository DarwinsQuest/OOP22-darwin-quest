package darwinsquest.controller;

import darwinsquest.core.gameobject.Nameable;
import darwinsquest.core.gameobject.element.Elemental;

/**
 * Interface that represents the controller of a {@link darwinsquest.core.gameobject.move.Move}.
 * It consists of an immutable view of {@link darwinsquest.core.gameobject.move.Move}.
 */
public interface MoveController extends Elemental, Nameable, Choosable {

}
