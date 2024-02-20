package me.n1ar4.jar.analyzer.analyze.spring.asm;

import me.n1ar4.jar.analyzer.analyze.spring.SpringConstant;
import me.n1ar4.jar.analyzer.analyze.spring.SpringController;
import me.n1ar4.jar.analyzer.core.ClassReference;
import me.n1ar4.jar.analyzer.core.MethodReference;
import me.n1ar4.jar.analyzer.starter.Const;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpringClassVisitor extends ClassVisitor {
    private final Map<ClassReference.Handle, ClassReference> classMap;
    private final Map<MethodReference.Handle, MethodReference> methodMap;
    private final List<SpringController> controllers;
    private boolean isSpring;
    private SpringController currentController;
    private String name;
    private SpringPathAnnoAdapter pathAnnoAdapter = null;

    public SpringClassVisitor(List<SpringController> controllers,
                              Map<ClassReference.Handle, ClassReference> classMap,
                              Map<MethodReference.Handle, MethodReference> methodMap) {
        super(Const.ASMVersion);
        this.methodMap = methodMap;
        this.controllers = controllers;
        this.classMap = classMap;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor av = super.visitAnnotation(descriptor, visible);
        if (descriptor.equals(SpringConstant.RequestMappingAnno)) {
            this.pathAnnoAdapter = new SpringPathAnnoAdapter(Const.ASMVersion, av);
            return this.pathAnnoAdapter;
        }
        return av;
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        this.name = name;
        Set<String> annotations = classMap.get(new ClassReference.Handle(name)).getAnnotations();
        if (annotations.contains(SpringConstant.ControllerAnno) ||
                annotations.contains(SpringConstant.RestControllerAnno) ||
                annotations.contains(SpringConstant.SBApplication)) {
            this.isSpring = true;
            currentController = new SpringController();
            currentController.setClassReference(classMap.get(new ClassReference.Handle(name)));
            currentController.setClassName(new ClassReference.Handle(name));
            currentController.setRest(!annotations.contains(SpringConstant.ControllerAnno));
            if (pathAnnoAdapter != null) {
                if (!pathAnnoAdapter.getResults().isEmpty()) {
                    currentController.setBasePath(pathAnnoAdapter.getResults().get(0));
                }
            }
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (this.isSpring) {
            if (this.name.equals("<init>") || this.name.equals("<clinit>")) {
                return mv;
            }
            if (pathAnnoAdapter != null) {
                if (!pathAnnoAdapter.getResults().isEmpty()) {
                    currentController.setBasePath(pathAnnoAdapter.getResults().get(0));
                }
            }
            return new SpringMethodAdapter(name, descriptor, this.name, Const.ASMVersion, mv,
                    currentController, this.methodMap);
        } else {
            return mv;
        }
    }

    @Override
    public void visitEnd() {
        if (isSpring) {
            controllers.add(currentController);
        }
        super.visitEnd();
    }
}
