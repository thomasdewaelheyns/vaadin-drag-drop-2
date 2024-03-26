package com.example.application.views.draganddrop.widget;

import com.example.application.views.draganddrop.config.WidgetConfig;
import com.example.application.views.draganddrop.events.CustomDragEndEvent;
import com.example.application.views.draganddrop.events.CustomDragStartEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dnd.DragEndEvent;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DragStartEvent;
import com.vaadin.flow.component.dnd.EffectAllowed;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;

/**
 * Created by Thomas on 25/03/2024.
 */
public class WidgetLayout extends VerticalLayout implements DragSource<Component> {

    private int widgetId;
    private WidgetConfig config;
    private Icon icon;

    public WidgetLayout(int widgetId, WidgetConfig config) {
        this.widgetId = widgetId;
        this.config = config;
        setup();
        setupDragAndDropListeners();
    }

    private void setup(){
        setSizeFull();
        getStyle().set("border-width","1px");
        getStyle().set("border-style","solid");
        getStyle().set("border-color","black");
        icon = VaadinIcon.ARROWS.create();
        add(icon);
        add(new Span("Widget "+config.getId()));
    }

    @Override
    public Element getDraggableElement() {
        return icon.getElement();
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
}
