/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.kafka.listener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @author Gary Russell
 * @since 2.9.3
 *
 */
public class ErrorHandlingCoverageTests {

	@Test
	void coverageAdapter() {
		FallbackBatchErrorHandler fbbeh = spy(new FallbackBatchErrorHandler());
		ErrorHandlerAdapter adapter = new ErrorHandlerAdapter(fbbeh);
		adapter.addNotRetryableExceptions(IllegalStateException.class);
		assertThat(fbbeh.getClassifier().classify(new IllegalStateException())).isFalse();
		adapter.setClassifications(Map.of(IllegalArgumentException.class, false), false);
		verify(fbbeh).setClassifications(Map.of(IllegalArgumentException.class, false), false);
		assertThat(adapter.removeClassification(IllegalStateException.class)).isNull();
		verify(fbbeh).removeClassification(IllegalStateException.class);
		assertThat(adapter.removeClassification(IllegalArgumentException.class)).isFalse();
		verify(fbbeh).removeClassification(IllegalArgumentException.class);
	}

	@Test
	void coverageDefault() {
		DefaultErrorHandler eh = new DefaultErrorHandler();
		eh.addNotRetryableExceptions(IllegalStateException.class);
		eh.setClassifications(Map.of(IllegalArgumentException.class, false), false);
		assertThat(eh.removeClassification(IllegalStateException.class)).isNull();
		assertThat(eh.removeClassification(IllegalArgumentException.class)).isFalse();
	}

}
