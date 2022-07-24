package org.dieschnittstelle.ess.basics;


import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.ess.basics.annotations.DisplayAS;
import org.dieschnittstelle.ess.basics.annotations.StockItemProxyImpl;

import java.lang.reflect.Field;

import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowAnnotations {

    public static void main(String[] args) throws IllegalAccessException {
        // we initialise the collection
        StockItemCollection collection = new StockItemCollection(
                "stockitems_annotations.xml", new AnnotatedStockItemBuilder());
        // we load the contents into the collection
        collection.load();

        for (IStockItem consumable : collection.getStockItems()) {
            showAttributes(((StockItemProxyImpl) consumable).getProxiedObject());
        }

        // we initialise a consumer
        Consumer consumer = new Consumer();
        // ... and let them consume
        consumer.doShopping(collection.getStockItems());
    }

    /*
     * TODO BAS2
     */
    private static void showAttributes(Object instance) throws IllegalAccessException {


        // TODO BAS2: create a string representation of instance by iterating
        //  over the object's attributes / fields as provided by its class
        //  and reading out the attribute values. The string representation
        //  will then be built from the field names and field values.
        //  Note that only read-access to fields via getters or direct access
        //  is required here.
        Field[] fields = instance.getClass().getDeclaredFields();
        int i = fields.length;

        System.out.print("{" + instance.getClass().getSimpleName() + " ");
        for (Field fld : fields) {
            try {
                fld.setAccessible(true);
                System.out.print(fld.getName() + " : " + fld.get(instance).toString() + " ");
                i=i-1;
                if(i>0)
                    System.out.print(", ");

            } catch (Exception e) {
                show(e.toString());
            }
        }

        System.out.println(" }");

        // TODO BAS3: if the new @DisplayAs annotation is present on a field,
        //  the string representation will not use the field's name, but the name
        //  specified in the the annotation. Regardless of @DisplayAs being present6
        //  or not, the field's value will be included in the string representation.

        show("class is: " + instance.getClass());
        StringBuilder attributeStringBas= new StringBuilder();
        int j =0;
        Class klassBas3 = instance.getClass();
        attributeStringBas.append("{");
        attributeStringBas.append(klassBas3.getSimpleName()).append(" ");
        for (Field field : instance.getClass().getDeclaredFields()) {
            j++;
            field.setAccessible(true);
            if(field.getAnnotation(DisplayAS.class)!=null){
                attributeStringBas.append(field.getAnnotation(DisplayAS.class).value());
            }else{
                attributeStringBas.append(field.getName());
            }
            attributeStringBas.append(":  ");

            attributeStringBas.append(field.get(instance)); //value of field
            if(j!=instance.getClass().getDeclaredFields().length)
                attributeStringBas.append(",  ");

        }
        attributeStringBas.append("}");
        System.out.println(attributeStringBas);
//
//
//        StringBuilder attributeString = new StringBuilder();
//        Class klass = instance.getClass();
//        attributeString.append(klass.getSimpleName()).append(" ");
//        for (Field field : instance.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            if (field.getAnnotation(DisplayAS.class)!=null) {
//                attributeString.append(field.getAnnotation(DisplayAS.class).value());
//            } else {
//                attributeString.append(field.getName());
//            }
//            attributeString.append(":  ");
//            attributeString.append(field.get(instance));
//            attributeString.append("  ");
//        }
//        System.out.println(attributeString);
    }

}
