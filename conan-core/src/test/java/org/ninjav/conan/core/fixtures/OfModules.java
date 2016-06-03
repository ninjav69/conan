package org.ninjav.conan.core.fixtures;

import org.ninjav.conan.core.module.PresentModuleUseCase;
import org.ninjav.conan.core.module.PresentableModule;
import org.ninjav.conan.core.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Query
public class OfModules {

    private List<Object> list(Object... objects) {
        return Arrays.asList(objects);
    }

    public List<Object> query() {
        User loggedInUser = ModulePresentation.gateKeeper.getLoggedInUser();
        PresentModuleUseCase useCase = new PresentModuleUseCase();
        List<PresentableModule> presentableCodecasts = useCase.presentModules(loggedInUser);
        List<Object> queryResponse = new ArrayList<>();
        for (PresentableModule pcc : presentableCodecasts) {
            queryResponse.add(makeRow(pcc.name, pcc.description, pcc.isViewable));
        }
        return queryResponse;

    }

    private List<Object> makeRow(String name, String description, boolean viewable) {
        return list(
                new Object[]{
                    list("name", name),
                    list("description", description),
                    list("viewable", viewable ? "+" : "-")}
        );
    }
}
