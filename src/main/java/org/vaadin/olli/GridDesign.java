package org.vaadin.olli;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * A Designer generated component for the grid-design template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("grid-design")
@HtmlImport("grid-design.html")
public class GridDesign extends PolymerTemplate<GridDesign.GridDesignModel> {

    @Id("hyphenless")
    private Grid<String> hyphenless;
    @Id("with-hyphen")
    private Grid<String> withHyphen;

    private List<String> data = new ArrayList<>();

    /**
     * Creates a new GridDesign.
     */
    public GridDesign() {
        for (int i = 0; i < 255; i++) {
            data.add(new UUID(i, i).toString());
        }
        addGfromVaadin(hyphenless, "foo");
        addGfromVaadin(withHyphen, "bar");
    }

    private void addGfromVaadin(Grid<String> g, String key) {
        g.setSelectionMode(Grid.SelectionMode.NONE);
        g.setHeightByRows(false);
        g.addComponentColumn(new ValueProvider<String, Div>() {

            private static final long serialVersionUID = 19417019178683266L;

            @Override
            public Div apply(String source) {

                Div d = new Div();
                d.addClassNames("notification-timeline_box");
                d.add(new Paragraph(source));
                return d;
            }

        });
        g.setDataProvider(DataProvider.fromCallbacks(query -> {
            int lim = query.getLimit();
            int off = query.getOffset();
            System.out.println(key + " fetch: " + off + "+" + lim);

            return fetchActions(off, lim);
        }, query -> {

            return getActionCount();
        }));

        g.setHeight("100vh");
        g.setPageSize(7);

    }

    private int getActionCount() {
        return data.size();
    }

    private Stream<String> fetchActions(int off, int lim) {
        return data.stream().skip(off).limit(lim);
    }

    /**
     * This model binds properties between GridDesign and grid-design
     */
    public interface GridDesignModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
