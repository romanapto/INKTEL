package com.app.repository.es.custom;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import com.app.filter.PaginationInfo;
import com.app.persistence.model.es.DomainObject;

public abstract class BaseRepositoryCustom<T extends DomainObject> {

	protected ElasticsearchRestTemplate elasticsearchTemplate;

	public BaseRepositoryCustom(ElasticsearchRestTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	abstract Class<T> getTClass();

	abstract IndexCoordinates getIndex();

	protected BoolQueryBuilder buildTextQueryString(String text, String... fields) {
		BoolQueryBuilder builder = boolQuery();
		if (StringUtils.isNotEmpty(text)) {
			QueryStringQueryBuilder queryStringBuilder = queryStringQuery("*" + text + "*");
			for (String field : fields) {
				queryStringBuilder = queryStringBuilder.field(field);
			}
			builder = builder.must(queryStringBuilder);
		} else {
			builder.must(matchAllQuery());
		}
		return builder;
	}

	protected void buildRangeFilter(BoolQueryBuilder filterBuilder, String name, Object from, Object to) {
		if (from != null || to != null) {
			RangeQueryBuilder rangeBuilder = rangeQuery(name);
			if (from != null) {
				rangeBuilder.gte(from);
			}
			if (to != null) {
				rangeBuilder.lte(to);
			}
			filterBuilder.must(rangeBuilder);
		}
	}

	protected void buildRangeNestedFilter(BoolQueryBuilder filterBuilder, String path, String name, Object from,
			Object to) {
		if (from != null || to != null) {
			RangeQueryBuilder rangeBuilder = rangeQuery(name);
			if (from != null) {
				rangeBuilder.gte(from);
			}
			if (to != null) {
				rangeBuilder.lte(to);
			}
			filterBuilder.must(nestedQuery(path, rangeBuilder, ScoreMode.None));
		}
	}

	protected SortBuilder<?> buildSort(PaginationInfo pagination) {
		return buildInnerSort(null, null, pagination.getSortBy(), pagination.getSortOrder());
	}

	protected SortBuilder<?> buildSort(String text, PaginationInfo pagination) {
		return buildInnerSort(text, null, pagination.getSortBy(), pagination.getSortOrder());
	}

	protected SortBuilder<?> buildSort(String sortBy, String sortOrder) {
		return buildInnerSort(null, null, sortBy, sortOrder);
	}

	protected SortBuilder<?> buildSort(String text, String sortBy, String sortOrder) {
		return buildInnerSort(text, null, sortBy, sortOrder);
	}

	protected SortBuilder<?> buildSort(String text, String nestedPath, String sortBy, String sortOrder) {
		return buildInnerSort(text, nestedPath, sortBy, sortOrder);
	}

	private SortBuilder<?> buildInnerSort(String text, String nestedPath, String sortBy, String sortOrder) {
		if (StringUtils.isNotEmpty(text)) {
			return SortBuilders.scoreSort();
		} else {
			FieldSortBuilder sort = SortBuilders.fieldSort(sortBy).unmappedType("string")
					.order(sortOrder.equals("desc") ? SortOrder.DESC : SortOrder.ASC);
			if (StringUtils.isNotEmpty(nestedPath)) {
				sort.setNestedPath(nestedPath);
			}

			return sort;
		}
	}

	protected Page<T> queryForPage(QueryBuilder filterBuilder, PageRequest pageRequest, SortBuilder<?> sortBuilder) {
		Query searchQuery = buildSearchQuery(null, filterBuilder, pageRequest, sortBuilder);

		return elasticsearchTemplate.queryForPage(searchQuery, getTClass(), getIndex());
	}

	protected Page<T> queryForPage(QueryBuilder searchBuilder, QueryBuilder filterBuilder, PageRequest pageRequest,
			SortBuilder<?> sortBuilder) {
		Query searchQuery = buildSearchQuery(searchBuilder, filterBuilder, pageRequest, sortBuilder);

		return elasticsearchTemplate.queryForPage(searchQuery, getTClass(), getIndex());
	}

	protected List<T> queryForList(QueryBuilder searchBuilder, QueryBuilder filterBuilder, PageRequest pageRequest,
			SortBuilder<?> sortBuilder) {
		Query searchQuery = buildSearchQuery(searchBuilder, filterBuilder, pageRequest, sortBuilder);

		return elasticsearchTemplate.queryForList(searchQuery, getTClass(), getIndex());
	}

	protected List<T> queryForList(QueryBuilder filterBuilder, PageRequest pageRequest, SortBuilder<?> sortBuilder) {
		Query searchQuery = buildSearchQuery(null, filterBuilder, pageRequest, sortBuilder);

		return elasticsearchTemplate.queryForList(searchQuery, getTClass(), getIndex());
	}

	protected Query buildSearchQuery(QueryBuilder searchBuilder, QueryBuilder filterBuilder,
			PageRequest pageRequest, SortBuilder<?> sortBuilder) {
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

		if (searchBuilder != null) {
			searchQueryBuilder = searchQueryBuilder.withQuery(searchBuilder);
		}
		if (filterBuilder != null) {
			searchQueryBuilder = searchQueryBuilder.withFilter(filterBuilder);
		}
		if (sortBuilder != null) {
			searchQueryBuilder = searchQueryBuilder.withSort(sortBuilder);
		}
		if (pageRequest != null) {
			searchQueryBuilder = searchQueryBuilder.withPageable(pageRequest);
		}

		return searchQueryBuilder.build();
	}

}
