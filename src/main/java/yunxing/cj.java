package yunxing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class cj {
    public Method[] met;
    public Object obj;
    public Map<String,Method> m=new HashMap<>();
    cj(Method[] mets,Object objs)
    {
        met=mets;
        obj=objs;
        for(int i=0;i<met.length;i++)
        {
            Method xxx=met[i];
            if (xxx.isAnnotationPresent(z2byxplugins.class))
            {
                z2byxplugins b=xxx.getAnnotation(z2byxplugins.class);
                m.put(b.type(),xxx);
            }
        }
    }
}
