package com.kagoshima.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.kagoshima.entity.Report;

@Service
public class ExcelService {

	private ResourceLoader resourceLoader;

	@Autowired
	public ExcelService(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Workbook createWorkbookWithReport(Report report) throws IOException {
		// テンプレートファイルの読み込み
		Resource resource = resourceLoader.getResource("classpath:template_report.xlsx");
		FileInputStream file = new FileInputStream(resource.getFile());
		Workbook workbook = new XSSFWorkbook(file);

		// シートの取得
		Sheet sheet = workbook.getSheetAt(0);

		// セルにデータの書き込み
		writeData(sheet, report);

		return workbook;
	}

	private void writeData(Sheet sheet, Report report) {
		// 上揃え、左揃え
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.TOP); // 上揃え
		style.setAlignment(HorizontalAlignment.LEFT); // 左揃え
		style.setWrapText(true); // 折り返して全体を表示する設定
		
		// 上揃え、左揃え、上罫線
        CellStyle styleBorderTop = sheet.getWorkbook().createCellStyle();
        styleBorderTop.setVerticalAlignment(VerticalAlignment.TOP);  // 上揃え
        styleBorderTop.setAlignment(HorizontalAlignment.LEFT);       // 左揃え
        style.setWrapText(true); // 折り返して全体を表示する設定
        styleBorderTop.setBorderTop(BorderStyle.THIN); // 上罫線の設定

        // 2行目
		Row row2 = sheet.getRow(1);
		if (row2 == null) {
			row2 = sheet.createRow(1);
		}
		
		// B2
		Cell cellB2 = row2.createCell(1);
		if(report.getReportMonth() != null) {
			cellB2.setCellValue(String.valueOf(report.getReportMonth()));
		}
		cellB2.setCellStyle(style);

		// 4行目
		Row row4 = sheet.getRow(3);
		if (row4 == null) {
			row4 = sheet.createRow(3);
		}
		
		// C4
		Cell cellC4 = row4.createCell(2);
		if(report.getEmployee().getFullName() != null) {
			cellC4.setCellValue(report.getEmployee().getFullName());
		}
		cellC4.setCellStyle(style);

		// 7行目
		Row row7 = sheet.getRow(6);
		if (row7 == null) {
			row7 = sheet.createRow(6);
		}
		
		// C7
		Cell cellC7 = row7.createCell(2);
		if(report.getEmployee().getAffiliation() != null) {
			cellC7.setCellValue(report.getEmployee().getAffiliation().getValue());
		}
		cellC7.setCellStyle(style);

		// 8行目
		Row row8 = sheet.getRow(7);
		if (row8 == null) {
			row8 = sheet.createRow(7);
		}
		
		// C8
		Cell cellC8 = row8.createCell(2);
		if(report.getContentBusiness() != null) {
			cellC8.setCellValue(report.getContentBusiness());
		}
		cellC8.setCellStyle(styleBorderTop);

		// 19行目
		Row row19 = sheet.getRow(18);
		if (row19 == null) {
			row19 = sheet.createRow(18);
		}
		
		// C19
		Cell cellC19 = row19.createCell(2);
		if(report.getTimeWorked() != null) {
			cellC19.setCellValue(report.getTimeWorked());
		}
		cellC19.setCellStyle(style);

		// E19
		Cell cellE19 = row19.createCell(4);
		if(report.getTimeOver() != null) {
			cellE19.setCellValue(report.getTimeOver());
		}
		cellE19.setCellStyle(style);

		// G19
		Cell cellG19 = row19.createCell(6);
		Integer rateBusiness = Optional.ofNullable(report.getRateBusiness()).orElse(-999);
		Integer rateStudy = Optional.ofNullable(report.getRateStudy()).orElse(-999);
		String rate = rateBusiness + " / " + rateStudy;
		cellG19.setCellValue(rate);
		cellG19.setCellStyle(style);

		// J19
		Cell cellJ19 = row19.createCell(9);
		if(report.getTrendBusiness() != null) {
			cellJ19.setCellValue(report.getTrendBusiness());
		}
		cellJ19.setCellStyle(style);

		// 20行目
		Row row20 = sheet.getRow(19);
		if (row20 == null) {
			row20 = sheet.createRow(19);
		}
		
		// C20
		Cell cellC20 = row20.createCell(2);
		if(report.getContentMember() != null) {
			cellC20.setCellValue(report.getContentMember());
		}
		cellC20.setCellStyle(styleBorderTop);

		// 31行目
		Row row31 = sheet.getRow(30);
		if (row31 == null) {
			row31 = sheet.createRow(30);
		}
		
		// C31
		Cell cellC31 = row31.createCell(2);
		if(report.getContentCustomer() != null) {
			cellC31.setCellValue(report.getContentCustomer());
		}
		cellC31.setCellStyle(style);

		// 39行目
		Row row39 = sheet.getRow(38);
		if (row39 == null) {
			row39 = sheet.createRow(38);
		}
		
		// C39
		Cell cellC39 = row39.createCell(2);
		if(report.getContentProblem() != null) {
			cellC39.setCellValue(report.getContentProblem());
		}
		cellC39.setCellStyle(style);

		// 50行目
		Row row50 = sheet.getRow(49);
		if (row50 == null) {
			row50 = sheet.createRow(49);
		}
		
		// D50
		Cell cellD50 = row50.createCell(3);
		if(report.getEvaluationBusiness() != null) {
			cellD50.setCellValue(report.getEvaluationBusiness());
		}
		cellD50.setCellStyle(style);

		// 53行目
		Row row53 = sheet.getRow(52);
		if (row53 == null) {
			row53 = sheet.createRow(52);
		}
		
		// D53
		Cell cellD53 = row53.createCell(3);
		if(report.getEvaluationStudy() != null) {
			cellD53.setCellValue(report.getEvaluationStudy());
		}
		cellD53.setCellStyle(style);

		// 56行目
		Row row56 = sheet.getRow(55);
		if (row56 == null) {
			row56 = sheet.createRow(55);
		}
		
		// D56
		Cell cellD56 = row56.createCell(3);
		if(report.getGoalBusiness() != null) {
			cellD56.setCellValue(report.getGoalBusiness());
		}
		cellD56.setCellStyle(style);

		// 59行目
		Row row59 = sheet.getRow(58);
		if (row59 == null) {
			row59 = sheet.createRow(58);
		}
		
		// D59
		Cell cellD59 = row59.createCell(3);
		if(report.getGoalStudy() != null) {
			cellD59.setCellValue(report.getGoalStudy());
		}
		cellD59.setCellStyle(style);

		// 62行目
		Row row62 = sheet.getRow(61);
		if (row62 == null) {
			row62 = sheet.createRow(61);
		}
		
		// C62
		Cell cellC62 = row62.createCell(2);
		if (report.getContentCompany() != null) {
			cellC62.setCellValue(report.getContentCompany());
		}
		cellC62.setCellStyle(style);

		// 68行目
		Row row68 = sheet.getRow(67);
		if (row68 == null) {
			row68 = sheet.createRow(67);
		}
		
		// C68
		Cell cellC68 = row68.createCell(2);
		if(report.getContentOthers() != null) {
			cellC68.setCellValue(report.getContentOthers());
		}
		cellC68.setCellStyle(style);
		
	}
}
