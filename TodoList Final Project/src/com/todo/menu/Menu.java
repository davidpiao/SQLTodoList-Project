package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList 명령어>");
        System.out.println("add - 아이템 추가");
        System.out.println("del - 아이템 삭제");
        System.out.println("edit - 아이템 수정");
        System.out.println("comp - 아이템 완료 체크");
        System.out.println("ls - 모든 아이템 보기");
        System.out.println("ls_comp - 완료된 아이템 보기");
        System.out.println("impt - 아이템 별표 체크");
        System.out.println("ls_impt - 중요한 아이템 보기");
        System.out.println("find - 키워드 찾기");
        System.out.println("find_cate - 카테고리 찾기");
        System.out.println("ls_cate - 모든 카테고리 보기");
        System.out.println("ls_name - 이름순으로 보기");
        System.out.println("ls_name_desc - 이름 역순으로 보기");
        System.out.println("ls_date - 날짜순으로 보기");
        System.out.println("ls_date_desc - 날짜 역순으로 보기");
        System.out.println("exit - 프로그램 종료");
    }
    public static void prompt() {
    	System.out.print("\n입력  > ");
    }
}
