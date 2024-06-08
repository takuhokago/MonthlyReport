INSERT INTO daily_report_system.employees(code,last_name,first_name,role,password,affiliation,delete_flg,created_at,updated_at)
     VALUES ("1","管理者","A","ADMIN","$2a$10$PpSqsG6kVXXSBXDzx5h5d..YrRuUEEjfifp3SWVU5pJRA1qOfbUdS","ICTSK",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO daily_report_system.employees(code,last_name,first_name,role,password,affiliation,delete_flg,created_at,updated_at)
     VALUES ("2","一般者","A","GENERAL","$2a$10$l16EO7683g5HY3KZk9Uh8e2cvj7Ipn7LQszprAHlj1EceATk8kGYS","ICTSS",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO daily_report_system.employees(code,last_name,first_name,role,password,affiliation,delete_flg,created_at,updated_at)
     VALUES ("3","一般者","B","GENERAL","$2a$10$8l0S8yqGya5.zDkg6CQTKeOGoCzfyFsOHXirFN/tvRfBATrIstHqW","ICTSS",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO daily_report_system.reports(report_date,employee_code,delete_flg,created_at,updated_at,content_business,time_worked,time_over,rate_business,rate_study,trend_business,content_member,content_customer,content_problem,evaluation_business,evaluation_study,goal_business,goal_study,content_company,content_others,complete_flg)
     VALUES (CURRENT_TIMESTAMP,1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,"業務内容",160,10,100,90,80,"その他メンバー関連内容","お客様情報","問題点内容","自己評価（先月業務目標）","自己評価（先月学習目標）","今月業務目標","今月学習目標","会社関係","その他",0);
INSERT INTO daily_report_system.reports(report_date,employee_code,delete_flg,created_at,updated_at,content_business,time_worked,time_over,rate_business,rate_study,trend_business,content_member,content_customer,content_problem,evaluation_business,evaluation_study,goal_business,goal_study,content_company,content_others,complete_flg)
     VALUES (CURRENT_TIMESTAMP,2,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,"業務内容",160,10,100,90,80,"その他メンバー関連内容","お客様情報","問題点内容","自己評価（先月業務目標）","自己評価（先月学習目標）","今月業務目標","今月学習目標","会社関係","その他",0);
INSERT INTO daily_report_system.reports(report_date,employee_code,delete_flg,created_at,updated_at,content_business,time_worked,time_over,rate_business,rate_study,trend_business,content_member,content_customer,content_problem,evaluation_business,evaluation_study,goal_business,goal_study,content_company,content_others,complete_flg)
     VALUES (CURRENT_TIMESTAMP,3,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,"業務内容",160,10,100,90,80,"その他メンバー関連内容","お客様情報","問題点内容","自己評価（先月業務目標）","自己評価（先月学習目標）","今月業務目標","今月学習目標","会社関係","その他",0);
