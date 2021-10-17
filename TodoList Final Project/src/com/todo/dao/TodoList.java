package com.todo.dao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.todo.service.DBconnect;

public class TodoList {
   Connection conn;

   public TodoList() {
      this.conn = DBconnect.getConnection();
   }
   
   public void importData(String filename) {
      try {
         BufferedReader br = new BufferedReader(new FileReader(filename));
         String line;
         String sql = "insert into list (title, memo, category, current_date, due_date)"
               + " values (?,?,?,?,?);";
         int records =0;
         while((line = br.readLine())!=null) {
            
            StringTokenizer st = new StringTokenizer(line, "##");
            String category = st.nextToken();
            String title = st.nextToken();
            String desc = st.nextToken();
            String due_date = st.nextToken();
            String current_date = st.nextToken();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, desc);
            pstmt.setString(3, category);
            pstmt.setString(4, current_date);
            pstmt.setString(5, due_date);
            int count = pstmt.executeUpdate();
            if(count > 0) records++;
            pstmt.close();
         }
         System.out.println(records +" records read!");
         br.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public int addItem(TodoItem t) {
      String sql = "insert into list (title, memo, category, current_date, due_date)"
            + " values (?,?,?,?,?);";
      PreparedStatement pstmt;
      int count=0;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, t.getTitle());
         pstmt.setString(2, t.getDesc());
         pstmt.setString(3, t.getCategory());
         pstmt.setString(4, t.getCurrent_date());
         pstmt.setString(5, t.getDue_date());
         count = pstmt.executeUpdate();
         pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      System.out.print(count);
      return count;
   }

   public int updateItem(TodoItem t) {
	      String sql ="update list set title=?, memo=?, category=?, current_date=?, due_date=?"
	            + " where id = ?;";
	      PreparedStatement pstmt;
	      int count=0;
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, t.getTitle());
	         pstmt.setString(2, t.getDesc());
	         pstmt.setString(3, t.getCategory());
	         pstmt.setString(4, t.getCurrent_date());
	         pstmt.setString(5, t.getDue_date());
	         pstmt.setInt(6, t.getNumber());
	         count = pstmt.executeUpdate();
	         pstmt.close();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return count;
	   }

	   public int deleteItem(int index) {
	      String sql = "delete from list where id=?;";
	      PreparedStatement pstmt;
	      int count=0;
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, index);
	         count = pstmt.executeUpdate();
	         pstmt.close();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return count;
	   }
   
   public ArrayList<TodoItem> getList() {
      ArrayList<TodoItem> list = new ArrayList<TodoItem>();
      Statement stmt;
      try {
         stmt = conn.createStatement();
         String sql = "SELECT * FROM list";
         ResultSet rs = stmt.executeQuery(sql);
         while(rs.next()) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            String title = rs.getString("title");
            String description = rs.getString("memo");
            String due_date = rs.getString("due_date");
            String current_date = rs.getString("current_date");
            int comp = rs.getInt("is_completed");
            TodoItem t = new TodoItem(title, description, category, due_date, comp);
            t.setNumber(id);
            t.setCurrent_date(current_date);
            list.add(t);
         }
         stmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return list;
   }
   
   public int getCount() {
      Statement stmt;
      int count = 0;
      try {
         stmt = conn.createStatement();
         String sql = "SELECT count(id) FROM list;";
         ResultSet rs = stmt.executeQuery(sql);
         rs.next();
         count = rs.getInt("count(id)");
         stmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return count;
   }
   
   public ArrayList<String> getCategories() {
	      ArrayList<String> list = new ArrayList<String>();
	      Statement stmt;
	      try {
	         stmt = conn.createStatement();
	         String sql = "SELECT DISTINCT category FROM list;";
	         ResultSet rs = stmt.executeQuery(sql);
	         while(rs.next()) {
	             String category = rs.getString("category");
	             list.add(category);
	         }
	      }catch (SQLException e) {
	         e.printStackTrace();
	      }
		return list;
	   }
	   
	   public ArrayList<TodoItem> getListCategory(String keyword) {
		   ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		   PreparedStatement pstmt;
		   try {
			   String sql = "SELECT * FROM list WHERE CATEGORY = ?";
			   pstmt = conn.prepareStatement(sql);
			   pstmt.setString(1, keyword);
			   ResultSet rs = pstmt.executeQuery();
			   while(rs.next()) {
		            int id = rs.getInt("id");
		            String category = rs.getString("category");
		            String title = rs.getString("title");
		            String description = rs.getString("memo");
		            String due_date = rs.getString("due_date");
		            String current_date = rs.getString("current_date");
		            TodoItem t = new TodoItem(title, description, category, due_date);
		            t.setNumber(id);
		            list.add(t);
		         }
		         pstmt.close();
		   } catch(SQLException e) {
			   e.printStackTrace();
		   }
		   return list;
	   }
	   
	   public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		   ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		   Statement stmt;
		   try {
			   stmt = conn.createStatement();
			   String sql = "SELECT * FROM list ORDER BY "+ orderby;
			   if (ordering == 0)
				   sql += " desc";
			   ResultSet rs = stmt.executeQuery(sql);
			   while(rs.next()) {
		            int id = rs.getInt("id");
		            String category = rs.getString("category");
		            String title = rs.getString("title");
		            String description = rs.getString("memo");
		            String due_date = rs.getString("due_date");
		            String current_date = rs.getString("current_date");
		            TodoItem t = new TodoItem(title, description, category, due_date);
		            t.setNumber(id);
		            list.add(t);
			   }
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
		   return list;
	   }
	   
   public ArrayList<TodoItem> findList(String keyword) {
      ArrayList<TodoItem> list = new ArrayList<TodoItem>();
      PreparedStatement pstmt;
      keyword = "%"+keyword+"%";
      try {
         String sql = "SELECT * FROM list WHERE title like ? or memo like ?;";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1,keyword);
         pstmt.setString(2,keyword);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            String title = rs.getString("title");
            String description = rs.getString("memo");
            String due_date = rs.getString("due_date");
            String current_date = rs.getString("current_date");
            TodoItem t = new TodoItem(title, description, category, due_date);
            t.setNumber(id);
            list.add(t);
         }
         pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   return list;
   }      

	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public int completed(int comp) {
		String sql = "update list set is_completed = 1 " + "where id = " + comp;
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public ArrayList<TodoItem> getCompList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_completed == 1;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setNumber(id);
				t.setIsComp(is_completed);
				list.add(t);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int important(int impt) {
		String sql = "update list set is_important = 1 " + "where id = " + impt;
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public ArrayList<TodoItem> getImptList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_important == 1;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int is_important = rs.getInt("is_important");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setNumber(id);
				t.setIsComp(is_completed);
				t.setIsImpt(is_important);
				list.add(t);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}