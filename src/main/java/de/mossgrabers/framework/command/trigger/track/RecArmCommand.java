// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2023
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.framework.command.trigger.track;

import de.mossgrabers.framework.command.core.AbstractTriggerCommand;
import de.mossgrabers.framework.configuration.Configuration;
import de.mossgrabers.framework.controller.IControlSurface;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.data.ITrack;
import de.mossgrabers.framework.daw.data.bank.ITrackBank;
import de.mossgrabers.framework.utils.ButtonEvent;

import java.util.Optional;


/**
 * A record arm button command.
 *
 * @param <S> The type of the control surface
 * @param <C> The type of the configuration
 *
 * @author Jürgen Moßgraber
 */
public class RecArmCommand<S extends IControlSurface<C>, C extends Configuration> extends AbstractTriggerCommand<S, C>
{
    private int index;


    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     */
    public RecArmCommand (final IModel model, final S surface)
    {
        this (-1, model, surface);
    }


    /**
     * Constructor. Toggles arming the track at the given index in the page of the current track
     * bank for recording.
     *
     * @param index The channel index
     * @param model The model
     * @param surface The surface
     */
    public RecArmCommand (final int index, final IModel model, final S surface)
    {
        super (model, surface);
        this.index = index;
    }


    /** {@inheritDoc} */
    @Override
    public void executeNormal (final ButtonEvent event)
    {
        if (event != ButtonEvent.UP)
            return;
        final ITrackBank currentTrackBank = this.model.getCurrentTrackBank ();
        if (this.index == -1)
        {
            final Optional<ITrack> selectedItem = currentTrackBank.getSelectedItem ();
            if (selectedItem.isPresent ())
                selectedItem.get ().toggleRecArm ();
        }
        else
            currentTrackBank.getItem (this.index).toggleRecArm ();
    }
}
