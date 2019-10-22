package com.PasswordManager;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginScreen 
{
	JFrame loginFrame;
	ArrayList<User> users;
	User myUser;
	boolean loggedIn;
	
	public LoginScreen()
	{
		loginFrame = null;
		myUser = null;
		loggedIn = false;
		pullUsers();
		launchLoginFrame();
	}
	
	@SuppressWarnings("unchecked")
	public void pullUsers()
	{
		users = new ArrayList<User>();
		
		try
		{
			File probeFile = new File("Users.jpss");
			probeFile.createNewFile();
			FileInputStream fis = new FileInputStream(probeFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			users = (ArrayList<User>) ois.readObject();
			
			ois.close();
			fis.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found.");
		}
		catch(IOException e)
		{
			System.out.println("Error initalizing stream.");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeUsers()
	{
		try
		{
			File probeFile = new File("Users.jpss");
			probeFile.createNewFile(); // Creates if it doesn't exist.
			FileOutputStream fos = new FileOutputStream(probeFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(users);
			
			oos.close();
			fos.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found.");
		}
		catch(IOException e)
		{
			System.out.println("Error initalizing stream.");
		}
		
		loggedIn = true;
	}
	
	public void launchLoginFrame()
	{
		loginFrame = new JFrame("JackPass - Login");
		loginFrame.setLayout(new GridLayout(3, 1));
		
		JLabel userLabel = new JLabel("Username");
		JLabel passLabel = new JLabel("Password");
		JTextField usernameBox = new JTextField();
		JTextField passwordBox = new JTextField();
		JButton createAccountButton = new JButton("Create");
		JButton logInButton = new JButton("Log In");
		
		loginFrame.add(userLabel);
		loginFrame.add(usernameBox);
		loginFrame.add(passLabel);
		loginFrame.add(passwordBox);
		loginFrame.add(createAccountButton);
		loginFrame.add(logInButton);
		
		createAccountButton.addActionListener(event -> createAccount(usernameBox.getText(), passwordBox.getText()));
		logInButton.addActionListener(event -> logIn(usernameBox.getText(), passwordBox.getText()));
		
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(200, 100);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		loginFrame.setLocation(d.width/2-loginFrame.getSize().width/2, d.height/2-loginFrame.getSize().height/2);
		loginFrame.pack();
		loginFrame.setVisible(true);
	}
	
	public void createAccount(String u, String p)
	{
		User newUser = new User(u, p);
		
		if(users.contains(newUser))
		{
			JOptionPane.showMessageDialog(null, "That username is taken.");
		}
		else
		{
			users.add(newUser);
			myUser = newUser;
			writeUsers();
			JOptionPane.showMessageDialog(null, "Created Account");
		}
	}
	
	public void logIn(String u, String p)
	{
		User newUser = new User(u, p);
		if(users.contains(newUser))
		{
			if(users.get(users.indexOf(newUser)).getPassword().equals(p))
			{
				myUser = newUser;
				loggedIn = true;
				JOptionPane.showMessageDialog(null, "Logged In ");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Incorrect Username or Password.");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Incorrect Username or Password.");
		}
	}
	
	public User getUser()
	{
		while(loggedIn == false)
		{
			// TODO For some reason, the rest of this method doesn't run unless this is here so fix this lol
			System.out.println("Not Logged In");
		}
		loginFrame.dispose();
		return myUser;
	}
}
