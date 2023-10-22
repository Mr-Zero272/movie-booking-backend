package com.thuongmoon.myproject.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thuongmoon.myproject.model.Movie;

public interface MovieDao extends JpaRepository<Movie, Long> {

//	@Query("SELECT t FROM Movie t WHERE t.title LIKE %?1%")		
//	List<Movie> findByTitleLike(String title);

	@Query("SELECT t FROM Movie t")
	Page<Movie> findAllMovies(Pageable pageable);

	@Query("SELECT t FROM Movie t")
	Page<Movie> findAllWithPagination(Pageable pageable);

	@Query("SELECT m FROM Movie m WHERE m.title LIKE %?1%")
	Page<Movie> findByName(String titile, Pageable pageable);
	
	@Query("SELECT DISTINCT m FROM Movie m INNER JOIN FETCH m.genres g INNER JOIN m.screenings s WHERE g.name LIKE (:genre) AND m.title LIKE (%:q%) AND s.screening_start >= DATE(:today)")
	Page<Movie> getMoviesForSchudulePage(
			Pageable pageable,
			@Param("q") String q,
			@Param("genre") String genre,
			@Param("today") String today);

	@Query("SELECT DISTINCT m FROM Movie m INNER JOIN FETCH m.genres g INNER JOIN FETCH m.screenings s WHERE g.id IN (:genreIds) AND s.type LIKE :type AND m.manufacturer IN (:manufacturers) AND m.title LIKE (%:q%)")
	Page<Movie> getMoviePaginationPage(
			Pageable pageable,
			@Param("q") String q,
			@Param("type") String type, 
			@Param("genreIds") List<Long> genreIds,
			@Param("manufacturers") List<String> manufacturers);
	
	@Query("SELECT DISTINCT m FROM Movie m INNER JOIN FETCH m.genres g INNER JOIN FETCH m.screenings s WHERE g.id IN (:genreIds) AND s.type LIKE (:type) AND m.title LIKE (%:q%)")
	Page<Movie> getMoviePaginationPageWithouManufacturers(
			Pageable pageable,
			@Param("q") String q,
			@Param("type") String type, 
			@Param("genreIds") List<Long> genreIds);
	
	@Query("SELECT DISTINCT m FROM Movie m INNER JOIN FETCH m.screenings s WHERE s.type LIKE (:type) AND m.manufacturer IN (:manufacturers) AND m.title LIKE (%:q%)")
	Page<Movie> getMoviePaginationPageWithouGenres(
			Pageable pageable,
			@Param("q") String q,
			@Param("type") String type, 
			@Param("manufacturers") List<String> manufacturers);
	
//	@Query(value = "SELECT DISTINCT m FROM Movie m INNER JOIN FETCH m.screenings s WHERE s.type LIKE (:type) AND m.title LIKE (%:q%)",
//			countQuery = "SELECT count(m) FROM Movie m INNER JOIN m.screenings s WHERE s.type LIKE (:type) AND m.title LIKE (%:q%)")
	
	@Query(value = "SELECT DISTINCT m FROM Movie m INNER JOIN FETCH m.screenings s WHERE s.type LIKE (:type) AND m.title LIKE (%:q%)")
	Page<Movie> getMoviePaginationPageWithoutManufacturersAndGenres(
			Pageable pageable,
			@Param("q") String q,
			@Param("type") String type);
	
	@Query("SELECT DISTINCT m.manufacturer FROM Movie m")
	List<String> getManufacturers();
	
//	@Query("SELECT t FROM Tutorial t WHERE t.published=:isPublished AND t.level BETWEEN :start AND :end")
//	List<Movie> findByLevelBetween(@Param("start") int start, @Param("end") int end,
//									  @Param("isPublished") boolean isPublished);

}
