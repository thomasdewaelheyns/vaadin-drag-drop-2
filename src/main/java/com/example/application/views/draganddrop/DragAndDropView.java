package com.example.application.views.draganddrop;

import com.example.application.views.MainLayout;
import com.example.application.views.draganddrop.config.ContainerConfig;
import com.example.application.views.draganddrop.container.ContainerLayout;
import com.example.application.views.draganddrop.events.CustomDragEndEvent;
import com.example.application.views.draganddrop.events.CustomDragStartEvent;
import com.example.application.views.draganddrop.slot.LayoutSlot;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dnd.DropEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.ArrayList;

@PageTitle("Drag and drop")
@Route(value = "draganddrop", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class DragAndDropView extends VerticalLayout {

    private ArrayList<LayoutSlot> slots = new ArrayList<>();

    public DragAndDropView() {
        setup();
    }

    private void setup(){
        setSizeFull();
        setSpacing(true);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        ContainerLayout container1 = new ContainerLayout(1, new ContainerConfig(1));
        ContainerLayout container2 = new ContainerLayout(2, new ContainerConfig(2));

        addLayoutSlot(1, container1);
        addLayoutSlot(2, container2);
    }

    private void addLayoutSlot(int slotId, ContainerLayout containerLayout){
        LayoutSlot layoutSlot = new LayoutSlot(slotId);
        layoutSlot.add(containerLayout);
        layoutSlot.addDropListener(this::handleDropEvent);
        slots.add(layoutSlot);
        add(layoutSlot);
    }

    private void handleDropEvent(DropEvent<LayoutSlot> dropEvent){
        if(dropEvent.getDragSourceComponent().isPresent()) {
            Component dragged = dropEvent.getDragSourceComponent().get();
            HasComponents draggedParent = (HasComponents) dragged.getParent().get();
            Component current = dropEvent.getComponent().getChildren().findFirst().get();
            dropEvent.getComponent().removeAll();
            dropEvent.getComponent().add(dragged);
            draggedParent.removeAll();
            draggedParent.add(current);
        } else {
            System.out.println("No drag source component available!");
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        ComponentUtil.addListener(UI.getCurrent(), CustomDragStartEvent.class, this::enableDropZones);
        ComponentUtil.addListener(UI.getCurrent(), CustomDragEndEvent.class, this::disableDropZones);
    }

    private void enableDropZones(CustomDragStartEvent dragStartEvent){
        /*boolean isContainer = dragStartEvent.getEventData() instanceof ContainerConfig;
        slots.forEach(s -> {
            s.setActive(isContainer);
            s.getStyle().set("border","1px solid red"+(isContainer?"green":"red"));
        });*/
    }

    private void disableDropZones(CustomDragEndEvent dragEndEvent){
        /*slots.forEach(s -> {
            s.setActive(false);
            s.getStyle().set("border",null);
        });*/
    }
}
