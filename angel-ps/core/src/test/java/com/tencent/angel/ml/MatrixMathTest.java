/*
 * Tencent is pleased to support the open source community by making Angel available.
 *
 * Copyright (C) 2017 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.angel.ml;

import junit.framework.Assert;

import org.junit.Test;

import com.tencent.angel.ml.math.matrix.LILIntMatrix;
import com.tencent.angel.utils.Time;

import java.util.Random;

public class MatrixMathTest {

  @Test
  public void LILIntMatrixTest() {

    int row, col;
    row = 10000;
    col = 10000;
    LILIntMatrix matrix = new LILIntMatrix(row, col);

    int[] rowIds = new int[col];
    int[] colIds = new int[col];
    int[] values = new int[col];

    int[] oldValues = new int[col];
    Random random = new Random(Time.monotonicNow());

    long cost = 0;
    for (int t = 0; t < 1000; t ++) {

      for (int i = 0; i < col; i ++) {
        rowIds[i] = random.nextInt(col);
        colIds[i] = i;
        values[i] = random.nextInt(col);
        oldValues[i] = matrix.get(rowIds[i], colIds[i]);
      }

      long start = Time.monotonicNow();
      matrix.inc(rowIds, colIds, values);
      long end   = Time.monotonicNow();

      for (int i = 0; i < col; i ++) {
        Assert.assertEquals(values[i] + oldValues[i], matrix.get(rowIds[i], colIds[i]));
      }

      cost += end - start;
    }

    System.out.println(cost);
  }
}
