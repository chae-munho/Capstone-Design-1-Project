package com.example.firstSpringboot.ioc;

import org.springframework.stereotype.Component;

@Component //해당 클래스를 객체로 만들고 이를 IOC 컨테이너에 등록! 안하면 Autowird해도 오류남
public class Chef {

    //셰프는 식재료 공장을 알고 있음
    private IngredientFactory ingredientFactory;
    //셰프가 식재료 공장과 협업하기 위한 DI
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }
    //셰프가 식재료 공장과 협업하기 위한 DI
    
    public String cook(String menu) {
        //재료 준비

































        Ingredient ingredient = ingredientFactory.get(menu);

        //요리 반환
        return ingredient.getName() + "으로 만든 " + menu;

    }
}
