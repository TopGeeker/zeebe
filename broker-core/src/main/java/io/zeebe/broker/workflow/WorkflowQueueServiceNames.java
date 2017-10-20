/*
 * Zeebe Broker Core
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.zeebe.broker.workflow;

import io.zeebe.logstreams.processor.StreamProcessorController;
import io.zeebe.servicecontainer.ServiceName;

public class WorkflowQueueServiceNames
{
    public static final ServiceName<WorkflowQueueManager> WORKFLOW_QUEUE_MANAGER = ServiceName.newServiceName("workflow.manager", WorkflowQueueManager.class);

    public static ServiceName<StreamProcessorController> workflowInstanceStreamProcessorServiceName(String queueName)
    {
        return ServiceName.newServiceName(String.format("workflow.%s.processor.instance", queueName), StreamProcessorController.class);
    }

    public static ServiceName<StreamProcessorController> incidentStreamProcessorServiceName(String queueName)
    {
        return ServiceName.newServiceName(String.format("workflow.%s.processor.incident", queueName), StreamProcessorController.class);
    }
}
