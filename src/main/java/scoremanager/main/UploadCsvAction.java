package scoremanager.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import tool.Action;

//ファイルアップロードを有効化

public class UploadCsvAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
			HttpSession session = request.getSession();
// 			セッションからログイン中のユーザー（Teacher）を取得
		    Teacher teacher = (Teacher)session.getAttribute("user");
		    
//		    アップロードされたCSVファイル
			Part filePart = request.getPart("csvFile");
			
			StudentDao dao = new StudentDao();
			
//			エラーメッセージをためるためのリスト
			List<String> errors = new ArrayList<>();
			
//			ファイル未選択チェック
			if (filePart == null || filePart.getSize() == 0) {
			    errors.add("CSVファイルを選択してください");
			    request.setAttribute("errors",errors);
			    return "upload_csv.jsp";
			}
			
//			ファイルを1行ずつ読める状態にする
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(filePart.getInputStream(),"UTF-8"))) {
				
				
				String line;
				boolean isFirstLine = true;
				int lineNumber = 0;
				
//				1行ずつ読み続ける
				while((line = br.readLine()) != null) {
					
					lineNumber++;
					
//					ヘッダーはスキップ
					if(isFirstLine) {
						isFirstLine = false;
						continue;
					}
					
					try {
						String[] data = line.split(",");
						
//						カラム数チェック
						if(data.length < 4) {
							errors.add(lineNumber + "行目: カラム不足");
							continue;
						}
					
						
//						データセット
						Student student = new Student();
						
						student.setNo(data[0]);
						student.setName(data[1]);
	                    student.setEntYear(Integer.parseInt(data[2]));
	                    student.setClassNum(data[3]);
	                    student.setIsAttend(true);
	                    student.setSchool(teacher.getSchool());
	                    
	                    dao.save(student);
                    
					} catch (Exception e) {
						errors.add(lineNumber + "行目: データ不足");
					}
				}
			}
			
			request.setAttribute("errors",errors);
			
			if (!errors.isEmpty()) {
			    // エラーあり → 元画面へ戻る
			    return "upload_csv.jsp";
			} else {
			    // 成功 → 完了画面へ
			    return "csv_result.jsp";
			}
	}
}