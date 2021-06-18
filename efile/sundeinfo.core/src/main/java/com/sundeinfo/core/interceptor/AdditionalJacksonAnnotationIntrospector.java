package com.sundeinfo.core.interceptor;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * 这里针对Jackson的返回值做附加处理
 * */
public class AdditionalJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {
    /* (non-Javadoc)
     * @see com.fasterxml.jackson.databind.interceptor.JacksonAnnotationIntrospector#findSerializer(com.fasterxml.jackson.databind.interceptor.Annotated)
     */
    @Override
    public Object findSerializer(Annotated annotated) {
        //只对方法有用
        if(annotated instanceof AnnotatedMethod) {

        }
        return super.findSerializer(annotated);
    }
}

