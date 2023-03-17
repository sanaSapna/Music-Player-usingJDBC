package com.jspider.musicplayerjdbc.operation;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.jspider.musicplayerjdbc.song.Song;
public class SongOperation {

	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static FileReader fileReader;
	private static Properties properties;
	private static ResultSet resultSet;
	private static String query;
	private static int result;
	private static Scanner scanner = new Scanner(System.in);
	private static String filePath = "D:\\WEJA1\\musicplayerjdbc\\resources\\db_info.properties";
	static Song song = new Song();

	private static void openConnection() {
		try {
			fileReader = new FileReader(filePath);
			properties = new Properties();
			properties.load(fileReader);

			Class.forName(properties.getProperty("driverPath"));

			connection = DriverManager.getConnection(properties.getProperty("dburl"), properties);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

	// Chose song to play
	public void choseToPlaySong() {
		System.out.println("Playing song...please wait");
		System.out.println("loading song list....");

		openConnection();
		query = "Select * from music_player " + "where id=?";
		try {
			preparedStatement = connection.prepareStatement(query);

			System.out.println("Enter song id to play song ");
			int id = scanner.nextInt();
			song.setId(id);
			preparedStatement.setInt(1, song.getId());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				song.setId(resultSet.getInt(1));
				song.setSongName(resultSet.getString(2));
				song.setSingerName(resultSet.getString(3));
				song.setMoveName(resultSet.getString(4));
				song.setDuration(resultSet.getDouble(5));
				System.out.println(song);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}

	}

	// Playing all song
	public void playAllSong() {
		System.out.println("Playing all song");

		openConnection();
		query="select * from music_player";
		
		try {
			preparedStatement=connection.prepareStatement(query);
			
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				song.setId(resultSet.getInt(1));
				song.setSongName(resultSet.getString(2));
				song.setSingerName(resultSet.getString(3));
				song.setMoveName(resultSet.getString(4));
				song.setDuration(resultSet.getDouble(5));
				System.out.println(song);
				System.out.println("-----------------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	// Adding song
	public void addSong() {
		System.out.println("Adding song in list");

		openConnection();
		query = "insert into " + "music_player values(?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			System.out.println("how many song you want to add:");
			int size = scanner.nextInt();
			for (int i = 1; i <= size; i++) {
				Song song = new Song();
				System.out.println();
				System.out.println("Enter id of song");
				int id = scanner.nextInt();
				song.setId(id);
				System.out.println("Enter song name");
				String name = scanner.next();
				song.setSongName(name);
				System.out.println("Enter singer name");
				String singer = scanner.next();
				 scanner.next();
				song.setSingerName(singer);
				System.out.println("enter move name");
				String move = scanner.next();
				song.setMoveName(move);
				System.out.println("Enter duration of song");
				double duration = scanner.nextDouble();
				song.setDuration(duration);

				preparedStatement.setInt(1, song.getId());
				preparedStatement.setString(2, song.getSongName());
				preparedStatement.setString(3, song.getSingerName());
				preparedStatement.setString(4, song.getMoveName());
				preparedStatement.setDouble(5, song.getDuration());

				result = preparedStatement.executeUpdate();

				System.out.println("Query ok " + result + "row(s) affected");

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			closeConnection();
			
		}

	}

	// Removing Song
	public void removeSong() {
		System.out.println("Removing song from list....");
		 
			try {
				openConnection();
				query = "Select * from music_player";

				preparedStatement = connection.prepareStatement(query);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					song.setId(resultSet.getInt(1));
					song.setSongName(resultSet.getString(2));
					song.setSingerName(resultSet.getString(3));
					song.setMoveName(resultSet.getString(4));
					song.setDuration(resultSet.getDouble(5));
					System.out.println(song);
					System.out.println("-----------------------");
				}
			

				query = "delete from music_player " + "where id=?";

				preparedStatement = connection.prepareStatement(query);

				System.out.println("Enter song id which you want to remove");
				int id = scanner.nextInt();
				song.setId(id);
				preparedStatement.setInt(1, song.getId());

				result = preparedStatement.executeUpdate();

				System.out.println("Query ok " + result + "rwo(s) affected");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		

	}

	// Updating song
	public void updateSong() {
		System.out.println("Updating song....");
		openConnection();
		query = "update music_player " + "set songName=?, singerName=?,moveName=?,duration=?  " + "where id=?";
		try {
			preparedStatement = connection.prepareStatement(query);

			System.out.println("Enter song id which you want to update ");
			int id = scanner.nextInt();
			song.setId(id);
			System.out.println("Enter updated song nmae");
			String sname = scanner.next();
			song.setSongName(sname);
			System.out.println("Enter updated singer name");
			String singer = scanner.next();
			song.setSingerName(singer);
			System.out.println("Enter updated move name");
			String move = scanner.next();
			song.setMoveName(move);
			System.out.println("Enter updated duration");
			double duration = scanner.nextDouble();
			song.setDuration(duration);

			preparedStatement.setString(1, song.getSongName());
			preparedStatement.setString(2, song.getSingerName());
			preparedStatement.setString(3, song.getMoveName());
			preparedStatement.setDouble(4, song.getDuration());
			preparedStatement.setInt(5,song.getId());
			
			result=preparedStatement.executeUpdate();
			
			System.out.println("Query ok "+ result+"row(s) affected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}

	}

}
