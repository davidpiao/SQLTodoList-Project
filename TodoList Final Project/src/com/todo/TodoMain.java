package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		
		boolean isList = false;
		boolean quit = false;
		TodoUtil.dueToday(l);
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "del_comp":
				TodoUtil.deleteItemChecked(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "comp":
				int comp = sc.nextInt();
				TodoUtil.checkComp(l, comp);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_comp":
				TodoUtil.listComp(l);
				break;
				
			case "impt":
				int impt = sc.nextInt();
				TodoUtil.checkImpt(l, impt);
				break;
				
			case "ls_impt":
				TodoUtil.listImpt(l);
				break;
				
			case "multi":
				int multi = sc.nextInt();
				TodoUtil.checkMulti(l, multi);
				break;
				
			case "ls_multi":
				TodoUtil.listMulti(l);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;				
				
			case "find_cate":
				String cate = sc.next();
				TodoUtil.findCategory(l, cate);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "ls_name":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;

			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;		
				
			case "save_json":
				System.out.print("json 파일로 저장하시겠습니까? y/n > ");
				String choices = sc.next();
				if(choices.equalsIgnoreCase("y")) {
					TodoUtil.GSONsave(l);
					break;
				}
				else break;
				
			case "load_json":
				TodoUtil.GSONload();
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				System.out.println("Goodbye!");
				quit = true;
				break;

			default:
				System.out.println("Please enter command from above. (도움말 - help)");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		sc.close();
	}
}
