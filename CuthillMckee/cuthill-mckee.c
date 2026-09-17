#include <stdio.h>
#include <stdlib.h>

typedef struct {
	int sortBy;
	int value;
} pqelement_t;

typedef struct {
	int size;
	pqelement_t* heap;
	int maxsize;
} priorityq_t;

// Priority queue functions.
void push(priorityq_t* pq, pqelement_t n) {
	if (pq->size == pq->maxsize) {return;}

	*(pq->heap + pq->size) = n;
	int i = pq->size;
	pq->size++;
	while ((*(pq->heap + i)).sortBy < (*(pq->heap + (i-1)/2)).sortBy && i >= 0) {
		pqelement_t t = *(pq->heap + i);
		*(pq->heap+i) = *(pq->heap + (i-1)/2);
		*(pq->heap + (i-1)/2) = t;
		i = (i - 1)/2;
	}
}

int pop(priorityq_t* pq) {
	if (pq->size == 0) {return -1;}
	pqelement_t n = *(pq->heap);
	pq->size--;
	(*(pq->heap)).sortBy = (*(pq->heap + pq->size)).sortBy;
	(*(pq->heap)).value = (*(pq->heap + pq->size)).value;
	int index = 0;
	int con = 0;
	while (con == 0) {
        int left_child = 2 * index + 1;
        int right_child = 2 * index + 2;
        int smallest = index;
        if (left_child < pq->size && (*(pq->heap + left_child)).sortBy < (*(pq->heap + smallest)).sortBy) {
            smallest = left_child;
        }
        if (right_child < pq->size && (*(pq->heap + right_child)).sortBy < (*(pq->heap + smallest)).sortBy) {
            smallest = right_child;
        }
        if (smallest != index) {
			pqelement_t t = *(pq->heap + index);
			*(pq->heap + index) = *(pq->heap + smallest);
			*(pq->heap + smallest) = t;

            index = smallest;
        } else {
            con = 1;
        }
    }
	return n.value;
}

priorityq_t instantiate(int max) {
	priorityq_t pq;
	pq.size = 0;
	pq.maxsize = max;
	pq.heap = (pqelement_t*) malloc(sizeof(pqelement_t)*max);
	return pq;
}

// Queue
typedef struct node {
	int value;
	struct node* next;
} node_t;

typedef struct queue {
	node_t *head;
	int size;
	node_t *end;
} queue_t;

// Queue methods.
int popq(queue_t* q) {
	if (q->size == 0) {return -1;}
	if (q->size == 1) {
		q->size = 0;
		int n = q->head->value;
		free(q->head);
		return n;
	}
	q->size = q->size - 1;
	int to_return = q->head->value;
	node_t* next = q->head->next;
	free(q->head);
	q->head = next;
	return to_return;
}

queue_t instantiateq() {
	queue_t q;
	q.head = (node_t*) malloc(sizeof(node_t));
	q.size = 0;
	return q;
}

void pushq(queue_t* q, int n) {
	if(q->size == 0) {
		q->end = (node_t*) malloc(sizeof(node_t));
		q->size = q->size + 1;;
		q->end->value = n;
		q->head = q->end;
		return;
	}
	q->end->next = (node_t*) malloc(sizeof(node_t));
	q->end->next->value = n;
	q->end = q->end->next;
	q->size = q->size + 1;
}
// End of data structures

int* getadj(int n, int* edges, int node) {
	int* adj = (int*) malloc(sizeof(int)*(n+1));
	int* cur = adj;
	for(int i = 0; *(edges+i) != -1; i+=2) {
		int v1 = *(edges+i);
		int v2 = *(edges+1+i);
		if(v1 == node) {*cur++ = v2;}
		if(v2 == node) {*cur++ = v1;}
	}
	*cur = -1;
	return adj;
}

int main() {
	int n = 6;
	// Lables start at 1
	int nodes[] = {1, 2, 3, 4, 5, 6};
	int visited[] = {0, 0, 0, 0, 0, 0, 0};
	// If edge (x, y) is included, edge (y, x) is assumed to be included.
	// Direction does not affect edge weight.
	int edges[] = {1, 3, 2, 3, 4, 3, 3, 5, 5, 6, -1};

	// Find node degrees.
	int* degrees = (int*) malloc(sizeof(int)*(n+1));
	for(int i = 0; i <= n; i++) {*(degrees + i) = 0;}
	for (int i = 0; *(edges+i) != -1; i+=2) {
		int n1 = *(edges + i);
		int n2 = *(edges + i+1);
		*(degrees + n1) = *(degrees + n1) + 1;
		*(degrees + n2) = *(degrees + n2) + 1;
	}

	priorityq_t pq = instantiate(n+1);
	int smallest = *(degrees+1);
	int smallnode = 1;
	for (int i = 1; i <= n; i++) {
		smallnode = smallest <= *(degrees + i) ? smallnode : i;
		smallest = smallest <= *(degrees + i) ? smallest : *(degrees + i);
	}
	int* labels = (int*) malloc(sizeof(int)*(n+1));
	int* curlabel = labels + 1;
	queue_t q = instantiateq();
	pushq(&q, smallnode);
	*(visited+smallnode) = 1;
	while(q.size > 0) {
		int cur = popq(&q);
		int* adj = getadj(n, edges, cur);
		for(int i = 0; *(adj + i) != -1; i++) {
			if (!*(visited+*(adj + i))) {
				pqelement_t pqe = {*(degrees + *(adj+i)), *(adj+i)};
				push(&pq, pqe);
				*(visited + *(adj+i)) = 1;
			}
		}
		while(pq.size > 0) {
			int temp = pop(&pq);
			pushq(&q, temp);
		}

		*(curlabel++) = cur;
		free(adj);
	}
	for(int i = 1; i <= n; i++) {
		printf("Old %d, New %d\n", *(labels + i), i);
	}

	free(labels);
	free(degrees);
	free(pq.heap);
	return 0;
}
