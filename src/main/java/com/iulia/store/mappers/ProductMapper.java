package com.iulia.store.mappers;

import com.iulia.store.dtos.ProductDto;
import com.iulia.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto productToProductDto(Product product);
}
