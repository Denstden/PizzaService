package ua.com.rd.pizzaservice.domain.order.state;

import ua.com.rd.pizzaservice.domain.order.Order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.lang.reflect.Field;

@Converter
public class StateConverter implements AttributeConverter<State, String>{
    @Override
    public String convertToDatabaseColumn(State state) {
        String className = state.getClass().getSimpleName();
        className = className.substring(0, className.length()-5);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className.charAt(0));
        for (int i=1;i<className.length();i++){
            char currentCharacter = className.charAt(i);
            if (Character.isUpperCase(currentCharacter)){
                stringBuilder.append('_').append(currentCharacter);
            }
            else{
                stringBuilder.append(Character.toUpperCase(currentCharacter));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public State convertToEntityAttribute(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        int i=0;
        while (i< s.length()){
            char currentChar = s.charAt(i);
            if (currentChar=='_'){
                i++;
                if (i < s.length()){
                    stringBuilder.append(s.charAt(i));
                    i++;
                }
                else {
                    break;
                }
            }
            else{
                stringBuilder.append(Character.toLowerCase(currentChar));
                i++;
            }
        }
        try {
            Field field = Order.class.getDeclaredField(String.valueOf(stringBuilder.append("State")));
            field.setAccessible(true);
            return (State) field.get(Order.class);
        } catch (Exception ex){
            return null;
        }
    }
}
