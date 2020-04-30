package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		try {

			// Create and prepare reader
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/movies.csv"));
			br.readLine();

			// Will hold all Genres
			ArrayList<Genre> genreNames = new ArrayList<>();
			
			// Will hold all Genres within 5 years of 2018
			ArrayList<Genre> genreNames5Y = new ArrayList<>();
			
			// Will hold all movies
			ArrayList<Movie> movies = new ArrayList<>();

			while(br.ready()) {

				// Preparing line
				String line = br.readLine().replaceAll("\"", " ").replaceAll(" ", "");
				
				// Prepares and takes year from line for movies list
				String yearString = line.replaceAll("(nogenreslisted)", "0");
				yearString = yearString.contains("(") ? yearString.substring(yearString.lastIndexOf("("), yearString.lastIndexOf(")")) : "0";
				
				int year = Integer.parseInt(yearString.replace("(", ""));
				
				// Cuts everything but genres
				line = line.contains("(nogenreslisted)") ? "N/A" : line.contains(")") ? line.substring(line.lastIndexOf(")") + 2) : line.substring(line.lastIndexOf(",") + 1);

				// Splits line along "|" and puts each genre found into genres[] as a Genre object
				String[] arr = line.split(Pattern.quote("|"));
				Genre[] genres = new Genre[arr.length];
				for(int i = 0; i < arr.length; i++)
					genres[i] = new Genre(arr[i]);

				// Checks each Genre in genres to see if it appears in genreNames by iterating genreNames for each genre in genres
				// If it does, the Genre in genreNames gets incremented
				// Otherwise, add it to genreNames
				boolean flag1 = true;
				for(int i = 0; i < genres.length; i++) {

					for(int j = 0; j < genreNames.size(); j++)
						if(genres[i].equals(genreNames.get(j))) {
							genreNames.get(j).increment();
							flag1 = false;
						}

					if(flag1)
						genreNames.add(genres[i]);
				}
				
				// Same as previous except it also check to make sure the data came from a movie within the past 5 years (only goes up to 2018 so I did 2013 to 2018)
				boolean flag2 = true;
				for(int i = 0; i < genres.length; i++) {

					for(int j = 0; j < genreNames5Y.size(); j++)
						if(genres[i].equals(genreNames5Y.get(j)) && year > 2012) {
							genreNames5Y.get(j).increment();
							flag2 = false;
						}

					if(flag2 && year > 2012)
						genreNames5Y.add(genres[i]);
				}
				
				boolean flag3 = true;

					for(int j = 0; j < movies.size(); j++)
						if(year == movies.get(j).getYear()) {
							movies.get(j).addGenres(genres);
							flag3 = false;
						}

					if(flag3)
						movies.add(new Movie(year, genres));

			}
	
			br.close();

			// Orders genreNames based on count
			for(int i = 1; i < genreNames.size(); i++) {
				if(genreNames.get(i).getCount() > genreNames.get(i - 1).getCount()) {
					genreNames.add(0, genreNames.remove(i));
					i = 0;
				}
			}

			// Orders genreNames5Y based on count
			for(int i = 1; i < genreNames5Y.size(); i++) {
				if(genreNames5Y.get(i).getCount() > genreNames5Y.get(i - 1).getCount()) {
					genreNames5Y.add(0, genreNames5Y.remove(i));
					i = 0;
				}
			}
			
			// Orders movies based on year
			for(int i = 1; i < movies.size(); i++) {
				if(movies.get(i).getYear() < movies.get(i - 1).getYear()) {
					Collections.swap(movies, i, i - 1);
					i = 0;
				}
			}
			
			

			// Number of movies for each genre (all) output
			FileWriter output = new FileWriter("src/main/output/Number of movies for each genre (all).txt");
			for(Genre genre : genreNames)
				output.write(genre.toString() + "\n");
			output.close();
			
			// Number of movies for each genre (last 5 years) output
			output = new FileWriter("src/main/output/Number of movies for each genre (last 5 years).txt");
			for(Genre genre : genreNames5Y)
				output.write(genre.toString() + "\n");
			output.close();
			
			output = new FileWriter("src/main/output/Each genre for each year.txt");
			String out = "";
			for(Movie movie : movies) {
				out += "\n" + movie.getYear();
				for(Genre genre : movie.getGenres()) {
					out += "\n" + genre;
				}
				out += "\n";
			}
			output.write(out);
			output.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
