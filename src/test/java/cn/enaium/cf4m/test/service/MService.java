package cn.enaium.cf4m.test.service;

import cn.enaium.cf4m.annotation.Service;
import cn.enaium.cf4m.service.ModuleService;
import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * @author Enaium
 */
@Service
public class MService implements ModuleService {
    @Override
    public void beforeEnable(ModuleProvider moduleProvider) {
        System.out.println(moduleProvider.getName() + " beforeEnable");
    }

    @Override
    public void beforeDisable(ModuleProvider moduleProvider) {
        System.out.println(moduleProvider.getName() + " beforeDisable");
    }

    @Override
    public void afterEnable(ModuleProvider moduleProvider) {
        System.out.println(moduleProvider.getName() + " afterEnable");
    }

    @Override
    public void afterDisable(ModuleProvider moduleProvider) {
        System.out.println(moduleProvider.getName() + " afterDisable");
    }
}
