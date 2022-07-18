package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    public MainView() {
        //create items for the layout
        Button button = new Button("Save date");
        DatePicker datePicker = new DatePicker("Pick a date");

        //create and set a horizontal layout
        final HorizontalLayout hl = new HorizontalLayout();
        hl.setDefaultVerticalComponentAlignment(Alignment.END);

        //add items to the layout's and layout's to main layout
        hl.add(datePicker, button);
        this.add(hl);

        //do functionality
        button.addClickListener(clic -> this.add(new Paragraph("Clicked: " + datePicker.getValue())));
    }

}
