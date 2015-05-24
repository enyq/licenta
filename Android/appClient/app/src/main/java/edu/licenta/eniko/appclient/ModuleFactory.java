package edu.licenta.eniko.appclient;

/**
 * Created by Eniko on 5/24/2015.
 */
public class ModuleFactory {
        //use getModule method to get object of type module
        public Module getModule(String moduleType){
            if(moduleType == null){
                return null;
            }
            if(moduleType.equals(ModuleEnum.COMFORT)){
                return new ComfortModule();
            } else if(moduleType.equals(ModuleEnum.SECURITY)){
                return new SecurityModule();
            }
            return null;
        }
}
