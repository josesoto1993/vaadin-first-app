package vaadin.crm.ui.view.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import vaadin.crm.service.CompanyService;
import vaadin.crm.service.ContactService;
import vaadin.crm.ui.MainLayout;

import javax.annotation.security.PermitAll;
import java.util.Map;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Vaadin CRM")
@PermitAll
public class DashboardView extends VerticalLayout {
    private final ContactService contactService;
    private final CompanyService companyService;

    public DashboardView(ContactService contactService,
                         CompanyService companyService) {
        this.contactService = contactService;
        this.companyService = companyService;

        configureDashboardView();

        add(
                getContactStats(),
                getCompaniesChart()
        );
    }

    private void configureDashboardView() {
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private Component getContactStats() {
        Span stats = new Span(contactService.count() + " contacts");
        stats.addClassName("contact-stats");
        return stats;
    }

    private Chart getCompaniesChart() {
        //create chart as pie chart
        Chart chart = new Chart(ChartType.PIE);

        //create dataset
        DataSeries dataSeries = new DataSeries();

        //look for values in dataset
        Map<String, Integer> companies = companyService.getStats();
        companies.forEach((company, employees) ->
                dataSeries.add(new DataSeriesItem(company, employees)));

        //set values in chart and return chart
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}