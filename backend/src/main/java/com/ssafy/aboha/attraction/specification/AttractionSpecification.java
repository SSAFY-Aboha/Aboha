package com.ssafy.aboha.attraction.specification;

import com.ssafy.aboha.attraction.domain.Attraction;
import org.springframework.data.jpa.domain.Specification;

public class AttractionSpecification {

    public static Specification<Attraction> hasSidoCode(Integer sidoCode) {
        return (root, query, criteriaBuilder) ->
                (sidoCode == null) ? null : criteriaBuilder.equal(root.get("sido").get("code"), sidoCode);
    }

    public static Specification<Attraction> hasGugunCode(Integer gugunCode) {
        return (root, query, criteriaBuilder) ->
                (gugunCode == null) ? null : criteriaBuilder.equal(root.get("gugun").get("code"), gugunCode);
    }

    public static Specification<Attraction> hasContentTypeId(Integer contentTypeId) {
        return (root, query, criteriaBuilder) ->
                (contentTypeId == null) ? null : criteriaBuilder.equal(root.get("contentType").get("id"), contentTypeId);
    }
}
