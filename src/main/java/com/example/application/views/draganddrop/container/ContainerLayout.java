package com.example.application.views.draganddrop.container;

import com.example.application.views.draganddrop.config.ContainerConfig;
import com.example.application.views.draganddrop.config.WidgetConfig;
import com.example.application.views.draganddrop.events.CustomDragEndEvent;
import com.example.application.views.draganddrop.events.CustomDragStartEvent;
import com.example.application.views.draganddrop.slot.LayoutSlot;
import com.example.application.views.draganddrop.widget.WidgetLayout;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dnd.*;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 25/03/2024.
 */
public class ContainerLayout extends VerticalLayout implements DragSource<Component> {

    private int containerId;
    private ContainerConfig config;

    private HorizontalLayout content;
    private Icon icon;
    private List<LayoutSlot> slots = new ArrayList<>();

    public ContainerLayout(int containerId, ContainerConfig config) {
        this.containerId = containerId;
        this.config = config;
        setup();
    }

    private void setup(){
        getStyle().set("border-width","1px");
        getStyle().set("border-style","solid");
        getStyle().set("border-color","black");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        icon = VaadinIcon.ARROWS.create();
        add(icon);
        add(new Span("Widget "+config.getId()));

        content = new HorizontalLayout();
        content.setSpacing(true);
        add(content);

        WidgetLayout widget1 = new WidgetLayout(containerId*10+1,new WidgetConfig(containerId*10+1));
        WidgetLayout widget2 = new WidgetLayout(containerId*10+2,new WidgetConfig(containerId*10+2));

        addLayoutSlot(1, widget1);
        addLayoutSlot(2, widget2);
        setupDragAndDropListeners();
    }

    private void addLayoutSlot(int slotId, WidgetLayout widgetLayout){
        LayoutSlot layoutSlot = new LayoutSlot(slotId);
        layoutSlot.setHeight("300px");
        layoutSlot.add(widgetLayout);
        layoutSlot.addDropListener(this::handleDropEvent);
        slots.add(layoutSlot);
        content.add(layoutSlot);
    }

    /*@Override
    public Element getDraggableElement() {
        return icon.getElement();
    }*/

    private void handleDropEvent(DropEvent<LayoutSlot> dropEvent){
        Component dragged = dropEvent.getDragSourceComponent().get();
        HasComponents draggedParent = (HasComponents)dragged.getParent().get();
        Component current = dropEvent.getComponent().getChildren().findFirst().get();
        dropEvent.getComponent().removeAll();
        dropEvent.getComponent().add(dragged);
        draggedParent.removeAll();
        draggedParent.add(current);
    }

    private void setupDragAndDropListeners(){
        setDraggable(true);
        setEffectAllowed(EffectAllowed.MOVE);
        addDragStartListener(this::handleDragStart);
        addDragEndListener(this::handleDragEnd);
    }

    private void handleDragStart(DragStartEvent<Component> dragStart){
        dragStart.setDragData(config);
        ComponentUtil.fireEvent(UI.getCurrent(), new CustomDragStartEvent(this, true, config));
    }

    private void handleDragEnd(DragEndEvent<Component> dragEndEvent){
        ComponentUtil.fireEvent(UI.getCurrent(), new CustomDragEndEvent(this, true));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        ComponentUtil.addListener(UI.getCurrent(), CustomDragStartEvent.class, this::enableDropZones);
        ComponentUtil.addListener(UI.getCurrent(), CustomDragEndEvent.class, this::disableDropZones);
    }

    private void enableDropZones(CustomDragStartEvent dragStartEvent){
        /*boolean isWidget = dragStartEvent.getEventData() instanceof WidgetConfig;
        slots.forEach(s -> {
            s.setActive(isWidget);
            s.getStyle().set("border","1px solid red"+(isWidget?"green":"red"));
        });*/
    }

    private void disableDropZones(CustomDragEndEvent dragEndEvent){
        /*slots.forEach(s -> {
            s.setActive(false);
            s.getStyle().set("border",null);
        });*/
    }
}
