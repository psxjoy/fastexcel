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

package org.apache.fesod.excel.enums;

/**
 * Header merge strategy for Excel writing.
 * <p>
 * This enum provides fine-grained control over how header cells are automatically merged.
 * Use this when you need more control than the simple boolean {@code automaticMergeHead} parameter.
 * </p>
 * <p>
 * When {@code headerMergeStrategy} is not set (null), the behavior is determined by
 * {@code automaticMergeHead} for backward compatibility:
 * <ul>
 *   <li>{@code automaticMergeHead == true} → {@code HeaderMergeStrategy.AUTO}</li>
 *   <li>{@code automaticMergeHead == false} → {@code HeaderMergeStrategy.NONE}</li>
 * </ul>
 * </p>
 *
 * @see org.apache.fesod.excel.write.metadata.WriteBasicParameter#getHeaderMergeStrategy()
 * @see org.apache.fesod.excel.write.builder.AbstractExcelWriterParameterBuilder#headerMergeStrategy(HeaderMergeStrategy)
 */
public enum HeaderMergeStrategy {
    /**
     * No automatic merge.
     * <p>
     * Disables all automatic header merging. Each header cell will remain separate.
     * </p>
     */
    NONE,

    /**
     * Only horizontal merge (same cells in the same row).
     * <p>
     * Merges cells horizontally when they have the same name in the same row.
     * Vertical merging is disabled, even if cells have the same name in different rows.
     * </p>
     * <p>
     * Use case: When you want to merge cells horizontally but prevent vertical merging
     * that might combine cells with different contexts.
     * </p>
     */
    HORIZONTAL_ONLY,

    /**
     * Only vertical merge (same cells in the same column).
     * <p>
     * Merges cells vertically when they have the same name in the same column.
     * Horizontal merging is disabled. Note: This strategy does not check context consistency
     * (unlike AUTO strategy), so cells with the same name but different parent headers may be merged.
     * </p>
     * <p>
     * Use case: When you want to merge cells vertically regardless of their horizontal context.
     * </p>
     */
    VERTICAL_ONLY,

    /**
     * Only full rectangle merge (all cells must form a complete rectangle with the same name).
     * <p>
     * Merges cells only when they form a complete rectangular region where all cells
     * have the same name. This prevents partial merges and ensures all merged regions
     * are valid rectangles.
     * </p>
     * <p>
     * Use case: When you want to ensure merged regions are always complete rectangles,
     * preventing incorrect merges like issue #666 where cells with the same name but
     * different contexts were incorrectly merged.
     * </p>
     */
    FULL_RECTANGLE,

    /**
     * Auto merge (default behavior for backward compatibility).
     * <p>
     * Automatically merges cells both horizontally and vertically when they have the same name.
     * This strategy includes context consistency validation to prevent incorrect merges:
     * cells with the same name but different parent headers (different context) will not be merged.
     * </p>
     * <p>
     * This is the default behavior when {@code automaticMergeHead} is set to {@code true}
     * and {@code headerMergeStrategy} is not explicitly set.
     * </p>
     * <p>
     * Use case: General purpose automatic merging with improved accuracy compared to
     * the old implementation.
     * </p>
     */
    AUTO
}

