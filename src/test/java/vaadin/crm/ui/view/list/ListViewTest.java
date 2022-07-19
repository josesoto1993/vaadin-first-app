package vaadin.crm.ui.view.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vaadin.crm.backend.entity.Contact;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected(){
        Grid<Contact> grid = listView.getGrid();
        Contact firstContact = getFirstItem(grid);

        ContactForm listForm = listView.getContactForm();

        //check if start visible (it shouldn't)
        assertFalse(listForm.isVisible());

        //select 1st item
        grid.asSingleSelect().setValue(firstContact);

        //check if now is visible (it should)
        assertTrue(listForm.isVisible());
        //check if its same user name
        assertEquals(firstContact.getFirstName(), listForm.firstName.getValue());
    }

    private Contact getFirstItem(Grid<Contact> grid) {
        return ((ListDataProvider<Contact>) grid.getDataProvider()).getItems().iterator().next();
    }
}