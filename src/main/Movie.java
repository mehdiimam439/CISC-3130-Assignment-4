package main;

import java.util.ArrayList;

public class Movie {
	private int year;
	private Genre[] genres;
	
	public Movie(int year, Genre[] genres) {
		this.year = year;
		this.genres = genres;
	}
	
	public Movie(Genre[] genres) {
		this(0, genres);
	}


	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Genre[] getGenres() {
		return genres;
	}

	public void setGenres(Genre[] genres) {
		this.genres = genres;
	}
	
	public void addGenres(Genre[] genres) {
		Genre[] genresList = new Genre[this.genres.length + genres.length];
		for(int i = 0; i < this.genres.length; i++)
			genresList[i] = this.genres[i];
		for(int i = this.genres.length; i < genresList.length; i++)
			genresList[i] = genres[i - this.genres.length];
		
	}
	
	public String toString() {
		String temp = "";
		for(int i = 0; i < genres.length - 1; i++)
			temp += genres[i] + "|";
		return "Year: " + year + "\nGenre(s): " + temp + genres[genres.length - 1];
	}

}
