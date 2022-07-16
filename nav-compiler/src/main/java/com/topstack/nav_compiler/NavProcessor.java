package com.topstack.nav_compiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.topstack.nav_annotation.Destination;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * 导航注解处理器
 *
 * @author Administrator
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.topstack.nav_annotation.Destination")
public class NavProcessor extends AbstractProcessor {

    private static final String PAGE_TYPE_ACTIVITY = "Activity";
    private static final String PAGE_TYPE_FRAGMENT = "Fragment";
    private static final String PAGE_TYPE_DIALOG = "dialog";
    private static final CharSequence OUTPUT_FILE_NAME = "destination.json";


    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "enter init.....");

        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        /**
         * 返回的集合将包括所有含Destination注解的页面
         */
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Destination.class);
        if (!elements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(elements, Destination.class, destMap);

            try {
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME);
                //目录一般为：app/build/intermediate/javac/debug/classes/
                String resourcePath = resource.toUri().getPath();
                //截取到demo目录下,拼接到assets目录
                String appPath = resourcePath.substring(0, resourcePath.indexOf("demo") + 5);
                String assetsPath = appPath + "src/main/assets";
                //判断文件夹是否存在,不存在则创建
                File file = new File(assetsPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                //将desMap内容写到Json中
                String content = JSON.toJSONString(destMap);
                //新的文件,如果此文件已存在则删除，并创建新文件写入
                File outputFile = new File(assetsPath, (String) OUTPUT_FILE_NAME);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                outputFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(content);
                outputStreamWriter.flush();

                fileOutputStream.close();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 收集路由信息到destMap中
     */
    private void handleDestination(Set<? extends Element> elements, Class<Destination> destinationClass, HashMap<String, JSONObject> destMap) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;

            //全类名
            String clazName = typeElement.getQualifiedName().toString();
            Destination annotation = typeElement.getAnnotation(destinationClass);
            String pageUrl = annotation.pageUrl();
            boolean asStarter = annotation.asStarter();
            int id = Math.abs(clazName.hashCode());
            //Activity,Dialog,Fragment
            String destType = getDestinationType(typeElement);
            if (destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "不同页面不允许使用相同类型的PageUrl:" + pageUrl);
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("clazName", clazName);
                jsonObject.put("pageUrl", pageUrl);
                jsonObject.put("asStarter", asStarter);
                jsonObject.put("id", id);
                jsonObject.put("destType", destType);

                destMap.put(pageUrl, jsonObject);
            }
        }
    }

    private String getDestinationType(TypeElement typeElement) {
        TypeMirror typeMirror = typeElement.getSuperclass();
        String superClazName = typeMirror.toString();

        if (superClazName.contains(PAGE_TYPE_ACTIVITY.toLowerCase())) {
            return PAGE_TYPE_ACTIVITY.toLowerCase();
        } else if (superClazName.contains(PAGE_TYPE_FRAGMENT.toLowerCase())) {
            return PAGE_TYPE_FRAGMENT.toLowerCase();
        } else if (superClazName.contains(PAGE_TYPE_DIALOG.toLowerCase())) {
            return PAGE_TYPE_DIALOG.toLowerCase();
        }
        //这个父类的类型是类的类型，或者是接口类型
        if (typeMirror instanceof DeclaredType) {
            Element element = ((DeclaredType) typeMirror).asElement();
            if (element instanceof TypeElement) {
                //递归,直到找到父类类型为定义的三种为止
                return getDestinationType((TypeElement) element);
            }
        }
        return null;
    }
}