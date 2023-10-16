package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = UserAggregate::class, subscriberName = "task-subs-stream"
)
class AnnotationBasedTaskEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedTaskEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: TaskCreatedEvent) {
        logger.info("Task created: {}", event.title)
    }

    @SubscribeEvent
    fun taskNameUpdatedSubscriber(event: UpdateTasksInfoEvent) {
        logger.info("Task got a new update: title: {} description: {}", event.title, event.description)
    }

    @SubscribeEvent
    fun taskExecutorAddedSubscriber(event: AddTaskExecutorEvent) {
        logger.info("Task got executor with id: {}", event.executor)
    }

    @SubscribeEvent
    fun taskStatusChangedSubscriber(event: ChangeTaskStatusEvent) {
        logger.info("Task has changed Status: name:{}", event.status.statusName)
    }
}