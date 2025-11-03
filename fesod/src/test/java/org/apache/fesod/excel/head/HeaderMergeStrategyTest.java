/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fesod.excel.head;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.fesod.excel.FastExcel;
import org.apache.fesod.excel.enums.HeaderMergeStrategy;
import org.apache.fesod.excel.util.TestFileUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Test for header merge strategy
 *
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class HeaderMergeStrategyTest {

    private static File fileNone;
    private static File fileHorizontalOnly;
    private static File fileVerticalOnly;
    private static File fileFullRectangle;
    private static File fileAuto;
    private static File fileIssue666;

    @BeforeAll
    public static void init() {
        fileNone = TestFileUtil.createNewFile("headerMergeStrategyNone.xlsx");
        fileHorizontalOnly = TestFileUtil.createNewFile("headerMergeStrategyHorizontalOnly.xlsx");
        fileVerticalOnly = TestFileUtil.createNewFile("headerMergeStrategyVerticalOnly.xlsx");
        fileFullRectangle = TestFileUtil.createNewFile("headerMergeStrategyFullRectangle.xlsx");
        fileAuto = TestFileUtil.createNewFile("headerMergeStrategyAuto.xlsx");
        fileIssue666 = TestFileUtil.createNewFile("headerMergeStrategyIssue666.xlsx");
    }

    /**
     * Test issue #666 scenario: dynamic head with same cell name but different context
     * Expected: C2 and D2 should not be merged
     */
    @Test
    public void testIssue666() {
        List<List<String>> multiHeader = new ArrayList<>();
        multiHeader.add(new ArrayList<>(Arrays.asList("head10")));
        multiHeader.add(new ArrayList<>(Arrays.asList("head20", "head21")));
        multiHeader.add(new ArrayList<>(Arrays.asList("head30", "head31")));
        multiHeader.add(new ArrayList<>(Arrays.asList("head40", "head31")));
        multiHeader.add(new ArrayList<>(Arrays.asList("head40", "head41")));

        // Test with FULL_RECTANGLE strategy - should not merge C2 and D2
        FastExcel.write(fileIssue666)
                .head(multiHeader)
                .headerMergeStrategy(HeaderMergeStrategy.FULL_RECTANGLE)
                .sheet()
                .doWrite(createTestData());

        // Verify that C2 and D2 are not merged
        try (org.apache.poi.ss.usermodel.Workbook workbook =
                org.apache.poi.ss.usermodel.WorkbookFactory.create(fileIssue666)) {
            Sheet sheet = workbook.getSheetAt(0);
            int mergedRegionCount = sheet.getNumMergedRegions();

            // Check all merged regions
            boolean c2AndD2Merged = false;
            for (int i = 0; i < mergedRegionCount; i++) {
                CellRangeAddress region = sheet.getMergedRegion(i);
                // C2 is row 1, col 2; D2 is row 1, col 3
                // If they are merged, there should be a region covering both
                if (region.getFirstRow() == 1 && region.getLastRow() == 1
                        && region.getFirstColumn() == 2 && region.getLastColumn() == 3) {
                    c2AndD2Merged = true;
                    break;
                }
            }

            // Assert that C2 and D2 are NOT merged
            Assertions.assertFalse(c2AndD2Merged, "C2 and D2 should not be merged in issue #666 scenario");
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify merged regions", e);
        }
    }

    @Test
    public void testNoneStrategy() {
        List<List<String>> head = createTestHead();
        FastExcel.write(fileNone)
                .head(head)
                .headerMergeStrategy(HeaderMergeStrategy.NONE)
                .sheet()
                .doWrite(createTestData());

        // Verify no merged regions
        try (org.apache.poi.ss.usermodel.Workbook workbook =
                org.apache.poi.ss.usermodel.WorkbookFactory.create(fileNone)) {
            Sheet sheet = workbook.getSheetAt(0);
            Assertions.assertEquals(0, sheet.getNumMergedRegions(), "NONE strategy should not create any merged regions");
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify merged regions", e);
        }
    }

    @Test
    public void testHorizontalOnlyStrategy() {
        List<List<String>> head = createTestHead();
        FastExcel.write(fileHorizontalOnly)
                .head(head)
                .headerMergeStrategy(HeaderMergeStrategy.HORIZONTAL_ONLY)
                .sheet()
                .doWrite(createTestData());

        // Verify only horizontal merges exist
        try (org.apache.poi.ss.usermodel.Workbook workbook =
                org.apache.poi.ss.usermodel.WorkbookFactory.create(fileHorizontalOnly)) {
            Sheet sheet = workbook.getSheetAt(0);
            int mergedRegionCount = sheet.getNumMergedRegions();

            // All merged regions should be horizontal only (same row)
            for (int i = 0; i < mergedRegionCount; i++) {
                CellRangeAddress region = sheet.getMergedRegion(i);
                Assertions.assertEquals(region.getFirstRow(), region.getLastRow(),
                        "HORIZONTAL_ONLY strategy should only merge cells in the same row");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify merged regions", e);
        }
    }

    @Test
    public void testVerticalOnlyStrategy() {
        List<List<String>> head = createTestHead();
        FastExcel.write(fileVerticalOnly)
                .head(head)
                .headerMergeStrategy(HeaderMergeStrategy.VERTICAL_ONLY)
                .sheet()
                .doWrite(createTestData());

        // Verify only vertical merges exist
        try (org.apache.poi.ss.usermodel.Workbook workbook =
                org.apache.poi.ss.usermodel.WorkbookFactory.create(fileVerticalOnly)) {
            Sheet sheet = workbook.getSheetAt(0);
            int mergedRegionCount = sheet.getNumMergedRegions();

            // All merged regions should be vertical only (same column)
            for (int i = 0; i < mergedRegionCount; i++) {
                CellRangeAddress region = sheet.getMergedRegion(i);
                Assertions.assertEquals(region.getFirstColumn(), region.getLastColumn(),
                        "VERTICAL_ONLY strategy should only merge cells in the same column");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify merged regions", e);
        }
    }

    @Test
    public void testFullRectangleStrategy() {
        List<List<String>> head = createTestHead();
        FastExcel.write(fileFullRectangle)
                .head(head)
                .headerMergeStrategy(HeaderMergeStrategy.FULL_RECTANGLE)
                .sheet()
                .doWrite(createTestData());

        // Verify all merged regions form valid rectangles
        try (org.apache.poi.ss.usermodel.Workbook workbook =
                org.apache.poi.ss.usermodel.WorkbookFactory.create(fileFullRectangle)) {
            Sheet sheet = workbook.getSheetAt(0);
            int mergedRegionCount = sheet.getNumMergedRegions();

            // All merged regions should be valid rectangles
            for (int i = 0; i < mergedRegionCount; i++) {
                CellRangeAddress region = sheet.getMergedRegion(i);
                // Verify rectangle is valid (not just a single cell)
                Assertions.assertTrue(
                        region.getFirstRow() != region.getLastRow()
                                || region.getFirstColumn() != region.getLastColumn(),
                        "Merged region should not be a single cell");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify merged regions", e);
        }
    }

    @Test
    public void testAutoStrategy() {
        List<List<String>> head = createTestHead();
        FastExcel.write(fileAuto)
                .head(head)
                .headerMergeStrategy(HeaderMergeStrategy.AUTO)
                .sheet()
                .doWrite(createTestData());

        // AUTO strategy should work similar to the old behavior but with context validation
        try (org.apache.poi.ss.usermodel.Workbook workbook =
                org.apache.poi.ss.usermodel.WorkbookFactory.create(fileAuto)) {
            Sheet sheet = workbook.getSheetAt(0);
            // Just verify that the file was created successfully
            Assertions.assertNotNull(sheet, "Sheet should be created");
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify merged regions", e);
        }
    }

    /**
     * Create test head data with mergeable cells
     */
    private List<List<String>> createTestHead() {
        List<List<String>> head = new ArrayList<>();
        // Row 0: ["A", "A", "B"]
        head.add(new ArrayList<>(Arrays.asList("A")));
        head.add(new ArrayList<>(Arrays.asList("A")));
        head.add(new ArrayList<>(Arrays.asList("B")));
        // Row 1: ["A1", "A2", "B1"]
        head.add(new ArrayList<>(Arrays.asList("A", "A1")));
        head.add(new ArrayList<>(Arrays.asList("A", "A2")));
        head.add(new ArrayList<>(Arrays.asList("B", "B1")));
        return head;
    }

    /**
     * Create test data
     */
    private List<List<Object>> createTestData() {
        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new ArrayList<>(Arrays.asList("A" + i, "B" + i, "C" + i)));
        }
        return data;
    }
}

