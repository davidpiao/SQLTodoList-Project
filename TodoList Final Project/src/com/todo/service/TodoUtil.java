package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.google.gson.Gson;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n"
				+ "제목 > ");
		
		title = sc.nextLine();
		
		if (l.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		
		System.out.print("카테고리 > ");
		category = sc.nextLine();
		
		System.out.print("내용 > ");
		desc = sc.nextLine();
		
		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
				
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(l.addItem(t) > 0)
			System.out.println("개 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		
		if(l.deleteItem(index) > 0)
			System.out.println("삭제되었습니다.");
	}
	
	public static void deleteItemChecked(TodoList l) {
		Scanner sc = new Scanner(System.in);
	    int count = 0;
	    for (TodoItem item : l.getList()) {
	    	if(item.getIsComp() == 1) {
	    		System.out.println(item.toString());
		    	System.out.print("삭제를 원하시나요? y/n ");
		    	String choice = sc.next();
		    	if(choice.equalsIgnoreCase("y")) {     
		    		 if (l.deleteItem(item.getNumber())>0)
		    			 count++;
		        	 }
		    	}
	    	}
	    System.out.printf("\n총 %d개의 항목을 삭제했습니다.\n",count);
	    }


	public static void updateItem(TodoList l) {
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		
		int index = sc.nextInt();
		
		System.out.print("새 제목 > ");
		new_title = sc.next();
		
		System.out.print("새 카테고리 > ");
		new_category = sc.next();
//		sc.nextLine();
		
		System.out.print("새 내용 > ");
		new_desc = sc.next();
		
		System.out.print("새 마감일자  > ");
		new_due_date = sc.next();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setNumber(index);
		if(l.updateItem(t)>0)
			System.out.println("수정되었습니다.");
	}

	public static void findList(TodoList l, String keyword) {		
		int count = 0;
		
		for(TodoItem item: l.findList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.", count);
	}
	
	public static void findCategory(TodoList l, String category) {
		int count = 0;
		for(TodoItem item : l.getList() ) {
			if(item.getCategory().equals(category)) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void listCategory(TodoList l) {
		Set<String> list = new HashSet<String>();
		for(TodoItem item : l.getList()) {
			list.add(item.getCategory());
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			String st = (String)it.next();
			System.out.print(st);
			if (it.hasNext()) {
				System.out.print(" / ");
			}
		}
		System.out.println("\nTotal of " + list.size() + " different categories in the list.");
	}
	
	public static void listAll(TodoList l) {
		int i = 0;
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		
		for (TodoItem item : l.getList()) {
			i++;
			System.out.println(item.toString());
		}
	}
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void checkComp(TodoList l, int comp) {
		if (l.completed(comp) > 0) System.out.println("완료로 체크하였습니다.");
	}

	public static void listComp(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getCompList()) {
			if(item.getIsComp() == 1) {
			System.out.println(item.toString());
			count++;
			}
		}
		System.out.printf("\n총 %d개의 항목이 완료되었습니다.", count);
	}
	
	public static void checkImpt(TodoList l, int impt) {
		if (l.important(impt) > 0) System.out.println("별표 표시하였습니다.");		
	}	
	
	public static void listImpt(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getImptList()) {
			if(item.getIsImpt() == 1) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.printf("\n총 %d개의 항목이 별표로 표시되어있습니다.", count);
	}
	public static void checkMulti(TodoList l, int multi) {
		if (l.multi(multi) > 0) System.out.println("멀티로 체크되었습니다.");				
	}
	
	public static void listMulti(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getMultiList()) {
			if(item.getIsMulti() == 1) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.printf("\n총 %d개의 항목이 멀티로 체크되어있습니다.", count);
	}
	public static void GSONsave(TodoList l) {
		Gson gson = new Gson();
		
		String jsonstr = gson.toJson(l.getList());
		System.out.println(jsonstr);
		
		try {
			FileWriter writer = new FileWriter("todolist2.txt");
			writer.write(jsonstr);
			writer.close();
			System.out.println("파일 저장 완료!");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void GSONload() {
		Gson gson = new Gson();
		String jsonstr2 = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("todolist2.txt"));
			jsonstr2 = br.readLine();
			br.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("파일에서 데이타 가져오기 성공!");
		
		TodoItem[] array = gson.fromJson(jsonstr2, TodoItem[].class);
		List<TodoItem> list = Arrays.asList(array);
		System.out.println("list1: " + list);
	}
	
	public static void dueToday(TodoList l) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		LocalDateTime now = LocalDateTime.now();  
		String current = dtf.format(now);
		int count = 0;
		//System.out.println(dtf.format(now));  
		for (TodoItem item : l.getList()) {
			if(item.getDue_date().equals(current)) {
			System.out.println(item.toString());
			count++;
			}
		}
		System.out.printf("\n위에 총 %d개의 항목이 오늘까지입니다. 어서 화이팅하세요!", count);
		System.out.println("\n-------------------------------------------");
	}
}

















