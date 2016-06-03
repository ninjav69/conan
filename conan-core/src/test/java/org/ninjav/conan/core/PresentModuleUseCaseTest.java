package org.ninjav.conan.core;

import org.ninjav.conan.core.module.PresentableModule;
import org.ninjav.conan.core.module.PresentModuleUseCase;
import org.ninjav.conan.core.persistence.MockCoreGateway;
import org.ninjav.conan.core.model.User;
import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PresentModuleUseCaseTest {

    private User user;
    private Module codecast;
    private PresentModuleUseCase useCase;

    @Before
    public void setUp() {
        Context.coreGateway = new MockCoreGateway();
        user = Context.coreGateway.save(new User("User"));
        codecast = Context.coreGateway.save(new Module());
        useCase = new PresentModuleUseCase();
    }

    @Test
    public void userWithoutViewLicense_cannotViewModule() throws Exception {
        assertFalse(useCase.isLicensedToViewModule(user, codecast));
    }

    @Test
    public void userWithViewLicense_canViewModule() throws Exception {
        License viewLicense = new License(user, codecast);
        Context.coreGateway.save(viewLicense);
        assertTrue(useCase.isLicensedToViewModule(user, codecast));
    }

    @Test
    public void userWithoutViewLicense_cannotViewOtherUsersModule() throws Exception {
        User otherUser = Context.coreGateway.save(new User("otherUser"));

        License viewLicense = new License(user, codecast);
        Context.coreGateway.save(viewLicense);
        assertFalse(useCase.isLicensedToViewModule(otherUser, codecast));
    }

    @Test
    public void presentingNoModules() throws Exception {
        Context.coreGateway.delete(codecast);
        List<PresentableModule> presentableModules = useCase.presentModules(user);

        assertEquals(0, presentableModules.size());
    }

    @Test
    public void presentOneModule() throws Exception {
        codecast.setName("Some Title");
        codecast.setDescription("Tomorrow");
        List<PresentableModule> presentableModules = useCase.presentModules(user);
        assertEquals(1, presentableModules.size());
        PresentableModule presentableModule = presentableModules.get(0);
        assertEquals("Some Title", presentableModule.name);
        assertEquals("Tomorrow", presentableModule.description);
    }

    @Test
    public void presentedModuleIsNotViewableIfNoLicense() throws Exception {
        List<PresentableModule> presentableModules = useCase.presentModules(user);
        PresentableModule presentableModule = presentableModules.get(0);
        assertFalse(presentableModule.isViewable);
    }

    @Test
    public void presentedModuleIsViewableIfLicenseExists() throws Exception {
        Context.coreGateway.save(new License(user, codecast));
        List<PresentableModule> presentableModules = useCase.presentModules(user);
        PresentableModule presentableModule = presentableModules.get(0);
        assertTrue(presentableModule.isViewable);
    }

}
