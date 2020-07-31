import java.lang.reflect.Method;
import java.util.*;

public class DataUtil {
    /**
     * Grouping according to the interface, when used for collection grouping, gets the grouping
     * T is to groupBy attribute , and this return value is to groupBy attribute value
     */
    public interface GroupBy<T> {
        T groupBy(Object obj) ;
    }
/**
 * Groups collections by properties
 * @param colls
 * @param gb
 * @return
 * extends Comparable<T>
 */

public static final <T,D> Map<T , List<D>> groupBy(Collection<D> colls , GroupBy<T> gb){
    Map<T ,List<D>> map = new HashMap<T, List<D>>();

    Iterator<D> iter = colls.iterator() ;

    while(iter.hasNext()) {
        D d = iter.next() ;
        T t = gb.groupBy(d) ;
        if(map.containsKey(t)) {
            map.get(t).add(d) ;
        } else {
            List<D> list = new ArrayList<D>() ;
            list.add(d) ;
            map.put(t, list) ;
        }
    }
    return map ;
}

    /**
     * Group by filedName
     * @param colls
     * @param fieldName
     * @return
     * extends Comparable<T>
     */
    public static final <T,D> Map<T ,List<D>> groupBy(Collection<D> colls ,String fieldName){
        return groupBy(colls,new GroupBy<T>(){
            @Override
            public T groupBy(Object obj){
                Object v=getFieldValueByName(obj,fieldName);
                return (T)v;
            }
        });
    }

    public static Object getFieldValueByName(Object o,String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
