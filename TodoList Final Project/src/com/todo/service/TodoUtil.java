package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("/Users/sungjinpark/Documents/springtools/workspace/TodoListSQLApp Project/todolist.txt"));
			String oneline;
			while((oneline = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				//String impt = st.nextToken();
				String category = st.nextToken();
				String title = st.nextToken();
				//String comp = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem t = new TodoItem(title, desc, category, due_date);
				t.setCurrent_date(current_date);
				if (l.isDuplicate(title)) {
					return;
				}
				else 
				l.addItem(t);
			} in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter("todolist.txt");
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
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
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		
		if(l.deleteItem(index) > 0)
			System.out.println("삭제되었습니다.");
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
		new_category = sc.nextLine();
		sc.nextLine();
		
		System.out.print("새 내용 > ");
		new_desc = sc.nextLine();
		
		System.out.print("새 마감일자  > ");
		new_due_date = sc.nextLine();
		
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
		int count = 0, i = 0;
		for(TodoItem item : l.getList() ) {
			i++;
			if(item.getCategory().equals(category)) {
				System.out.println((i+1) + ". " + item.toString());
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
			System.out.println(i + ". " + item.toString());
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
		int i = 0;
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			i++;
			System.out.println(i + ". " + item.toString());
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
}

















