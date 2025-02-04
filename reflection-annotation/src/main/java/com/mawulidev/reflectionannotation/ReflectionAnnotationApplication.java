package com.mawulidev.reflectionannotation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ReflectionAnnotationApplication implements CommandLineRunner {
    private final WorkerService workerService;

    public ReflectionAnnotationApplication(WorkerService workerService) {
        this.workerService = workerService;
    }

    public static void main(String[] args) {
        try {
            // Assuming the compiled .class file is stored in "custom-classes/"
            CustomClassLoader loader = new CustomClassLoader("custom-classes/");
            Class<?> loadedClass = loader.loadClass("com.mawulidev.reflectionannotation.DynamicClass"); // Full class name

            // Using Reflection to invoke a methods
            Method[] declaredMethods = loadedClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Object instance = loadedClass.getDeclaredConstructor().newInstance();
                method.invoke(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpringApplication.run(ReflectionAnnotationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        workerService.performWork();
    }
}
