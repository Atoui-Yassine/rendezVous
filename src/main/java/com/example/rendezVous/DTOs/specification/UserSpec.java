package com.example.rendezVous.DTOs.specification;

import com.example.rendezVous.models.Categories.Category;
import com.example.rendezVous.models.adress.Address;
import com.example.rendezVous.models.userModel.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Objects;

@AllArgsConstructor
public class UserSpec implements Specification<UserModel> {

    private UserParams userParams;

    @Override
    public Predicate toPredicate(Root<UserModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate =cb.conjunction();

        Join<UserModel, Address> addressJoin = root.join("address", JoinType.INNER);
        Join<UserModel, Category> categoryJoin = root.join("category", JoinType.INNER);
        if(userParams.getCountry()!=null){
            Path expression = addressJoin.get("country");
            predicate=cb.and(cb.like(expression,"%"+ userParams.getCountry() + "%"), predicate);
        }
        if(userParams.getCat_name()!=null){
            Path expression = categoryJoin.get("name");

            predicate=cb.and(cb.like(expression,"%"+ userParams.getCat_name().toUpperCase() + "%"), predicate);
        }



        if(userParams.getSortDir().equalsIgnoreCase("asc")){
            query.orderBy(cb.asc(root.get(userParams.getSortBy())));

        } else if (userParams.getSortDir().equalsIgnoreCase("desc")) {
            query.orderBy(cb.desc(root.get(userParams.getSortBy())));
        }else {
            query.orderBy(cb.asc(root.get(userParams.getSortBy())));
        }



        return predicate;
    }

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 100));
    }


}
