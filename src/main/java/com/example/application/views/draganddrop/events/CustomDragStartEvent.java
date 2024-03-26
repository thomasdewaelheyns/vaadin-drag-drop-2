package com.example.application.views.draganddrop.events;

import com.example.application.views.draganddrop.config.BaseConfig;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

/**
 * Created by Thomas on 21/03/2024.
 */
public class CustomDragStartEvent extends ComponentEvent<Component> {

    private BaseConfig eventData;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     *                   side, <code>false</code> otherwise
     */
    public CustomDragStartEvent(Component source, boolean fromClient, BaseConfig eventData) {
        super(source, fromClient);
        this.eventData = eventData;
    }

    public BaseConfig getEventData() {
        return eventData;
    }
}
