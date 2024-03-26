package com.example.application.views.draganddrop.slot;

import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Created by Thomas on 25/03/2024.
 */
public class LayoutSlot extends VerticalLayout implements DropTarget<LayoutSlot> {

    private int slotId;

    public LayoutSlot(int slotId){
        this.slotId = slotId;
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        setActive(true);
    }
}
