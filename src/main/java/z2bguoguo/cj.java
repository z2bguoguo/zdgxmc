package z2bguoguo;

import java.lang.reflect.Method;
import java.util.Arrays;
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
            if (xxx.isAnnotationPresent(z2bplugins.class))
            {
                z2bplugins b=xxx.getAnnotation(z2bplugins.class);
                m.put(b.type(),xxx);
            }
        }
    }
}
