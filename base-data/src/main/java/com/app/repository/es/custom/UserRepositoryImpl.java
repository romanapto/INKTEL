package com.app.repository.es.custom;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import com.app.filter.PaginationInfo;
import com.app.filter.UserFilter;
import com.app.persistence.model.es.user.User;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

	@Autowired
	private ElasticsearchRestTemplate elasticsearchTemplate;

	@Override
	public Page<User> findUsers(UserFilter filter, PaginationInfo paginationInfo) {

		SortBuilder<?> sort;
		BoolQueryBuilder builder = boolQuery();
		if (StringUtils.isNotEmpty(filter.getText())) {
			builder.must(
					queryStringQuery("*" + filter.getText() + "*").field("firstName").field("lastName").field("email"));
			sort = SortBuilders.scoreSort();
		} else {
			builder.must(matchAllQuery());
			sort = SortBuilders.fieldSort(paginationInfo.getSortBy())
					.order(paginationInfo.getSortOrder().equals("desc") ? SortOrder.DESC : SortOrder.ASC)
					.unmappedType("string");
		}

		// Filters
		BoolQueryBuilder filterBuilder = boolQuery();
		// accountName
		if (CollectionUtils.isNotEmpty(filter.getAccountNames())) {
			filterBuilder.must(nestedQuery("organizations",
					termsQuery("organizations.accountName", filter.getAccountNames()), ScoreMode.None));
		}
		// roles
		if (CollectionUtils.isNotEmpty(filter.getRoles())) {
			filterBuilder.must(
					nestedQuery("roles", termsQuery("roles.roleName.keyword", filter.getRoles()), ScoreMode.None));
		}

		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(builder)
				.withFilter(filterBuilder)
				.withPageable(PageRequest.of(paginationInfo.getFrom(), paginationInfo.getSize())).withSort(sort);

		Query searchQuery = searchQueryBuilder.build();

		return elasticsearchTemplate.queryForPage(searchQuery, User.class, IndexCoordinates.of("sc-user"));
	}

}
