package cn.enaium.cf4m.test.processor;

import cn.enaium.cf4m.annotation.Processor;
import cn.enaium.cf4m.processor.ModuleProcessor;
import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * @author Enaium
 */
@Processor
public class MProcessor implements ModuleProcessor {
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
