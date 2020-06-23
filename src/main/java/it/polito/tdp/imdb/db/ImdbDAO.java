package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public void listAllActors(Map<Integer, Actor> idMap){
		String sql = "SELECT * FROM actors "
				+ "order by last_name ASC";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
                if(!idMap.containsKey(res.getInt("id"))) {
				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				System.out.println(actor);
				idMap.put(res.getInt("id"), actor);
				result.add(actor);
				//idMap.put(res.getInt("id"), actor);
				//System.out.println(idMap);
                }
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		System.out.println("Ehiiii"+idMap);
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
		
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getGeneri(){
		String sql = "select distinct `genre` " + 
				"from `movies_genres` " + 
				"order by genre ASC";
		
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				
				result.add(res.getString("genre"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	public List<Actor> getAttoriPerGenere(String genere, Map<Integer,Actor> mappa ){
		String sql = "select  distinct r.`actor_id` as id " + 
				"from roles as r, movies_genres as s " + 
				"where r.`movie_id` = s.`movie_id` and s.`genre` =? ";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();
		System.out.println(mappa.size());

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), mappa.get(res.getInt("id")).getFirstName(),mappa.get(res.getInt("id")).getLastName(),mappa.get(res.getInt("id")).getGender());
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Arco> getArchi(String genere, Map<Integer,Actor> idMap){
		String sql = "select r1.`actor_id` as id1,r2.`actor_id` as id2, count(*) as cnt " + 
				"from roles as r1, roles as r2, movies_genres as s " + 
				"where r1.`movie_id` = r2.`movie_id` and r1.`actor_id`>r2.`actor_id` and r1.`movie_id` = s.`movie_id` and "
				+ "r2.`movie_id` = s.`movie_id` and s.`genre` = ? " + 
				"group by r1.`actor_id`, r2.`actor_id`";
		
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Arco a = new Arco(idMap.get(res.getInt("id1")), idMap.get(res.getInt("id2")), res.getInt("cnt"));
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
}
