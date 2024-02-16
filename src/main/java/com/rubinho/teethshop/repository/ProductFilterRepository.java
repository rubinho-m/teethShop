package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.utils.FiltersData;
import com.rubinho.teethshop.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class ProductFilterRepository {
    @PersistenceContext
    EntityManager entityManager;

    public Page<Product> getFilteredPagedProducts(Pageable paging, FiltersData filters) {

        List<String> filterList = filters.getAllFilledInFields();
        Map<String, String> valuesList = filters.getValuesMap();


        StringBuilder builder = new StringBuilder();
        StringBuilder whereBuilder;

        if (filterList.isEmpty()) {
            whereBuilder = new StringBuilder("WHERE 1 = 1").append(System.lineSeparator());
        } else {
            whereBuilder = new StringBuilder();
        }


        builder
                .append("SELECT products.* FROM app_product products")
                .append(System.lineSeparator());

        for (String filter : filterList) {
            String manyForm = filter + "s";
            whereBuilder
                    .append("JOIN product_")
                    .append(manyForm)
                    .append(" ")
                    .append(manyForm)
                    .append(" on products.product_")
                    .append(filter)
                    .append("_id = ")
                    .append(manyForm)
                    .append(".id")
                    .append(System.lineSeparator());

        }

        for (int i = 0; i < filterList.size(); i++) {
            String filter = filterList.get(i);
            String manyForm = filter + "s";

            String[] values = valuesList.get(filter).split(",");
            System.out.println(Arrays.toString(values));


            if (i == 0) {
                whereBuilder.append("WHERE ");

            } else  {
                whereBuilder.append(" and ");
            }

            for (int j = 0; j < values.length; j++) {
                String value = values[j];

                if (j != 0){
                    whereBuilder.append(" or ");
                }

                whereBuilder.append(manyForm)
                        .append(".")
                        .append(filter)
                        .append("_name = '")
                        .append(value)
                        .append("'");

            }



            if (i == filterList.size() - 1){
                whereBuilder.append(System.lineSeparator());
            }



        }

        builder
                .append(whereBuilder);

        String nativeQuery = builder.toString();
        System.out.println(nativeQuery);
        Query query = entityManager.createNativeQuery(nativeQuery, Product.class);

        query.setMaxResults(paging.getPageSize());
        query.setFirstResult(paging.getPageNumber() * paging.getPageSize());

        List<Product> products = query.getResultList();

        return (Page<Product>) new PageImpl(
                products,
                paging,
                products.size());
    }

}
