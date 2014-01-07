/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.mrunit.internal.mapreduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MockReduceContextWrapperTest {

  class TestReducer extends Reducer {
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
      super.setup(context);
      assertEquals(10, context.getNumReduceTasks());
    }
  }


  /**
   * Tests that method context.getNumReduceTasks() correctly returns value from configuration
   * */
  @Test
  public void testContext_getNumReduceTasks() throws Exception {
    Reducer reducer = new TestReducer();
    ReduceDriver driver = ReduceDriver.newReduceDriver(reducer);
    driver.getConfiguration().setInt("mapred.reduce.tasks", 10);

    List<Text> values = new ArrayList();
    values.add(new Text("bb"));
    driver.withInput(new Text("aa"), values);
    assertEquals("Reducer not executed", 1, driver.run().size());

  }
}
